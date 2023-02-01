package commaproject.be.commaserver.common.config;

import commaproject.be.commaserver.service.dto.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
@PropertySource(value = "classpath:application-auth.yml")
public class JwtConfig {

    private final JwtProperties jwtProperties;

}
