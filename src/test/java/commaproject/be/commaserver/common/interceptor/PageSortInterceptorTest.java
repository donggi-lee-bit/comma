package commaproject.be.commaserver.common.interceptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import commaproject.be.commaserver.common.exception.common.NotAllowedPageSortException;
import commaproject.be.commaserver.integration.InitIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

@DisplayName("인터셉터 페이지 정렬 테스트")
class PageSortInterceptorTest extends InitIntegrationTest {

    @Autowired
    private PageSortInterceptor pageSortInterceptor;

    private MockHttpServletRequest mockHttpServletRequest;

    @BeforeEach
    void setUp() {
        mockHttpServletRequest = new MockHttpServletRequest();
    }

    @Test
    @DisplayName("정렬이 포함된 페이지 요청이 오면 예외를 발생시킨다")
    void if_page_sort_param_throw_exception() {
        mockHttpServletRequest.setParameter("sort", "asc");
        mockHttpServletRequest.setMethod("GET");
        assertThatThrownBy(() -> pageSortInterceptor.preHandle(mockHttpServletRequest, null, null))
            .isInstanceOf(NotAllowedPageSortException.class);
    }

    @Test
    @DisplayName("정렬이 포함되지 않은 일반적인 페이지 요청이 오면 true 를 반환한다")
    void no_sorting_request() throws Exception {
        mockHttpServletRequest.setParameter("page", "1");
        mockHttpServletRequest.setParameter("size", "10");
        boolean result = pageSortInterceptor.preHandle(mockHttpServletRequest, null, null);
        assertThat(result).isTrue();
    }
}
