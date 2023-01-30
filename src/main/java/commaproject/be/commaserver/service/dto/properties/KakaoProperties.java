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
    // 여기까지 카카오 데브 홈페이지에서 필요하다고 한 내용

    private final String accessTokenUri;
    private final String userInformationUri;
    // private final String loginPageUrl; --> 찍먹에는 추가되어있던 내용
}
