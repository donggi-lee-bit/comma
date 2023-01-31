package commaproject.be.commaserver.common.config;

import commaproject.be.commaserver.service.dto.properties.KakaoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties(KakaoProperties.class)
@PropertySource(value = "classpath:application-auth.yml")
@RequiredArgsConstructor
public class KakaoConfig {

    private final KakaoProperties kakaoProperties;
}
