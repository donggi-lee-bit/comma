package commaproject.be.commaserver.common.interceptor;

import commaproject.be.commaserver.common.exception.common.NotAllowedPageSortException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class PageSortInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        String sortParam = request.getParameter("sort");

        if (request.getMethod().equals("GET") && sortParam != null && !sortParam.isEmpty()) {
            throw new NotAllowedPageSortException();
        }

        return true;
    }
}
