package commaproject.be.commaserver.common.argumentresolver;

import commaproject.be.commaserver.common.exception.InvalidUserException;
import commaproject.be.commaserver.domain.user.AuthenticatedUser;
import commaproject.be.commaserver.service.JwtProvider;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class OauthLoginArgumentResolver implements HandlerMethodArgumentResolver {

    private static String AUTHORIZATION_HEADER = "Authorization";
    private static String TOKEN_TYPE = "Bearer ";

    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String token = parseAuthorizationHeader(request);

        Long loginUserId = Long.parseLong(jwtProvider.decode(token));
        return loginUserId;
    }

    private static String parseAuthorizationHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (Objects.isNull(authorizationHeader) || authorizationHeader.isEmpty()) {
            throw new InvalidUserException();
        }

        return authorizationHeader.replace(TOKEN_TYPE, "");
    }
}
