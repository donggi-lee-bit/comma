package commaproject.be.commaserver.tool;

import commaproject.be.commaserver.common.argumentresolver.CustomPageArgumentResolver;
import commaproject.be.commaserver.common.argumentresolver.OauthLoginArgumentResolver;
import commaproject.be.commaserver.common.config.WebMvcConfig;
import commaproject.be.commaserver.common.interceptor.PageSortInterceptor;
import commaproject.be.commaserver.service.JwtProvider;
import commaproject.be.commaserver.service.dto.properties.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@TestConfiguration
@EnableConfigurationProperties(value = {JwtProperties.class})
public class TestWebConfig implements WebMvcConfigurer {

    @Autowired
    JwtProperties jwtProperties;

    @Bean
    WebMvcConfig getWebMvcConfig() {
        return new WebMvcConfig(getOauthLoginArgumentResolver(), getCustomPageableArgumentResolver());
    }

    @Bean
    JwtProvider getJwtProvider() {
        return new JwtProvider(jwtProperties);
    }

    @Bean
    PageSortInterceptor getPageSortInterceptor() {
        return new PageSortInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getPageSortInterceptor());
    }

    OauthLoginArgumentResolver getOauthLoginArgumentResolver() {
        return new OauthLoginArgumentResolver(getJwtProvider());
    }

    CustomPageArgumentResolver getCustomPageableArgumentResolver() {
        return new CustomPageArgumentResolver();
    }
}
