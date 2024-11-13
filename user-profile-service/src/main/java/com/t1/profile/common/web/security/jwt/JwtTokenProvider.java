package com.t1.profile.common.web.security.jwt;

import com.t1.profile.common.web.security.exception.jwt.JwtTokenExpiredException;
import com.t1.profile.common.web.security.exception.jwt.JwtTokenIllegalArgumentException;
import com.t1.profile.common.web.security.exception.jwt.JwtTokenMalformedException;
import com.t1.profile.common.web.security.exception.jwt.JwtTokenUnsupportedException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String getUserNameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(authToken)
                    .getBody();
            String jti = claims.getId();
            String username = redisTemplate.opsForValue().get(jti);
            if (username == null) {
            // Токен недействителен
                return false;
            }
            return true;
        } catch (MalformedJwtException ex) {
            throw new JwtTokenMalformedException(ex.getMessage());
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenExpiredException(ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenUnsupportedException(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenIllegalArgumentException(ex.getMessage());
        }
    }
}
