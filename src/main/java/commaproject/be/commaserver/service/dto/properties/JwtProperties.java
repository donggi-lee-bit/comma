package commaproject.be.commaserver.service.dto.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "jwt")
@RequiredArgsConstructor
@ConstructorBinding
public class JwtProperties {

    private final String secretKey;
    private final Long accessTokenExpiredTime;
    private final Long refreshTokenExpiredTime;
    private final String accessTokenSubject;
    private final String refreshTokenSubject;
}
