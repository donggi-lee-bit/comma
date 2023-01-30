package commaproject.be.commaserver.service;

import commaproject.be.commaserver.service.dto.properties.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JwtProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(
            jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Long userId) {
        return generateToken(userId,
            jwtProperties.getAccessTokenSubject(),
            jwtProperties.getAccessTokenExpiredTime());
    }

    public String generateRefreshToken(Long userId) {
        return generateToken(userId,
            jwtProperties.getRefreshTokenSubject(),
            jwtProperties.getRefreshTokenExpiredTime());
    }

    private String generateToken(Long userId, String subject, Long expiredTime) {
        long now = System.currentTimeMillis();
        Date expiration = new Date(now + expiredTime);

        return Jwts.builder()
            .setSubject(subject)
            .setAudience(String.valueOf(userId))
            .setExpiration(expiration)
            .signWith(secretKey)
            .compact();
    }
}
