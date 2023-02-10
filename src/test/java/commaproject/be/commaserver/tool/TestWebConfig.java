package commaproject.be.commaserver.tool;

import commaproject.be.commaserver.common.argumentresolver.OauthLoginArgumentResolver;
import commaproject.be.commaserver.common.config.WebMvcConfig;
import commaproject.be.commaserver.service.JwtProvider;
import commaproject.be.commaserver.service.dto.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
@EnableConfigurationProperties(value = {JwtProperties.class})
public class TestWebConfig {

    @Bean
    WebMvcConfig getWebMvcConfig() {
        return new WebMvcConfig(getOauthLoginArgumentResolver());
    }

    @Autowired
    JwtProperties jwtProperties;

    @Bean
    JwtProvider getJwtProvider() {
        return new JwtProvider(jwtProperties);
    }

    @Bean
    @Primary
    OauthLoginArgumentResolver getOauthLoginArgumentResolver() {
        return new OauthLoginArgumentResolver(getJwtProvider());
    }
}
