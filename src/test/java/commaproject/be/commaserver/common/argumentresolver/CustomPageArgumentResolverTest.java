package commaproject.be.commaserver.common.argumentresolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import commaproject.be.commaserver.common.exception.common.InvalidPageParameterException;
import commaproject.be.commaserver.integration.InitIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

@DisplayName("페이지 메타데이터 ArgumentResolver 테스트")
class CustomPageArgumentResolverTest extends InitIntegrationTest {

    @Autowired
    private CustomPageArgumentResolver customPageArgumentResolver;

    private MockHttpServletRequest mockHttpServletRequest;

    @BeforeEach
    void setUp() {
        mockHttpServletRequest = new MockHttpServletRequest();
    }

    @Test
    @DisplayName("페이지 요청에 페이지 번호가 없으면 기본 페이지 값인 1을 반환한다")
    void no_page_number_page_number_is_1() throws Exception {
        Object result = customPageArgumentResolver.resolveArgument(null, null, new ServletWebRequest(mockHttpServletRequest), null);

        Pageable pageRequest = (Pageable) result;

        assertThat(pageRequest.getPageNumber()).isEqualTo(1);
    }

    @Test
    @DisplayName("게시글 페이징 요청에 페이지 사이즈가 없으면 전역으로 선언한 기본 사이즈 값인 10을 반환한다")
    void no_page_size_page_size_is_100() throws Exception {
        Object result = customPageArgumentResolver.resolveArgument(null, null, new ServletWebRequest(mockHttpServletRequest), null);

        Pageable pageRequest = (Pageable) result;

        assertThat(pageRequest.getPageSize()).isEqualTo(10);
    }

    @Test
    @DisplayName("페이지 사이즈가 100보다 크면 최대 페이지 사이즈인 100으로 바꾼다")
    void over_page_size_exchange_max_page_size() throws Exception {
        mockHttpServletRequest.setParameter("size", "101");
        Object result = customPageArgumentResolver.resolveArgument(null, null, new ServletWebRequest(mockHttpServletRequest), null);

        Pageable pageRequest = (Pageable) result;

        assertThat(pageRequest.getPageSize()).isEqualTo(100);
    }

    @Test
    @DisplayName("페이지 메타데이터가 숫자가 아닌 문자로 들어오면 예외를 발생시킨다")
    void page_meta_data_is_not_number() {
        mockHttpServletRequest.setParameter("size", "@");

        assertThatThrownBy(() -> customPageArgumentResolver.resolveArgument(null, null,
            new ServletWebRequest(mockHttpServletRequest), null))
            .isInstanceOf(InvalidPageParameterException.class);
    }
}
