package com.t1.profile.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    // Генерация токена
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

    // Получение имени пользователя из токена
    public String getUserNameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    // Валидация токена
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            // Некорректная подпись JWT
        } catch (MalformedJwtException ex) {
            // Невалидный JWT
        } catch (ExpiredJwtException ex) {
            // Истек срок действия JWT
        } catch (UnsupportedJwtException ex) {
            // Неподдерживаемый JWT
        } catch (IllegalArgumentException ex) {
            // Пустой строковый аргумент
        }
        return false;
    }
}
