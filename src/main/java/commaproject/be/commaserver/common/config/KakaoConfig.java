package commaproject.be.commaserver.common.config;

import commaproject.be.commaserver.service.dto.properties.KakaoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(KakaoProperties.class)
@RequiredArgsConstructor
public class KakaoConfig {

    private final KakaoProperties kakaoProperties;
}
