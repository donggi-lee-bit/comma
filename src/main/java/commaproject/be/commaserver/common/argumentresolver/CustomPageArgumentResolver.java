package commaproject.be.commaserver.common.argumentresolver;

import commaproject.be.commaserver.common.exception.common.InvalidPageParameterException;
import commaproject.be.commaserver.domain.comma.ValidPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@Slf4j
public class CustomPageArgumentResolver implements HandlerMethodArgumentResolver {

    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int MAX_PAGE_SIZE = 100;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(ValidPage.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Integer page = getIntegerParameter(webRequest, "page");
        Integer size = getIntegerParameter(webRequest, "size");

        int pageNumber = (page != null) ? page : DEFAULT_PAGE_NUMBER;
        int pageSize = (size != null) ? size : DEFAULT_PAGE_SIZE;

        if (pageSize > MAX_PAGE_SIZE) {
            pageSize = MAX_PAGE_SIZE;
        }

        log.info("page: {}, size: {}", pageNumber, pageSize);
        return PageRequest.of(pageNumber, pageSize);
    }

    private Integer getIntegerParameter(NativeWebRequest webRequest, String parameter) {
        String stringValue = webRequest.getParameter(parameter);
        if (stringValue != null) {
            try {
                return Integer.parseInt(stringValue);
            } catch (NumberFormatException e) {
                throw new InvalidPageParameterException();
            }
        }

        return null;
    }
}
