package commaproject.be.commaserver.common.config;

import commaproject.be.commaserver.common.argumentresolver.CustomPageArgumentResolver;
import commaproject.be.commaserver.common.argumentresolver.OauthLoginArgumentResolver;
import commaproject.be.commaserver.common.interceptor.PageSortInterceptor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final OauthLoginArgumentResolver oauthLoginArgumentResolver;
    private final CustomPageArgumentResolver customPageArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(oauthLoginArgumentResolver);
        resolvers.add(customPageArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PageSortInterceptor())
            .addPathPatterns("/api/commas");
    }
}
