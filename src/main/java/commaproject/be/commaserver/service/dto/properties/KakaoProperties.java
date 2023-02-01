package commaproject.be.commaserver.service.dto.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "kakao")
@RequiredArgsConstructor
@ConstructorBinding
public class KakaoProperties {

    private final String grantType;
    private final String clientId;
    private final String redirectUri;
    private final String responseType;
}
