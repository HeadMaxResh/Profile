package com.t1.profile.auth_service.security.jwt;

import com.t1.profile.auth_service.exception.jwt.JwtTokenExpiredException;
import com.t1.profile.auth_service.exception.jwt.JwtTokenIllegalArgumentException;
import com.t1.profile.auth_service.exception.jwt.JwtTokenMalformedException;
import com.t1.profile.auth_service.exception.jwt.JwtTokenUnsupportedException;
import com.t1.profile.auth_service.security.details.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenProvider {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
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
