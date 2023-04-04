package commaproject.be.commaserver.integration;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import commaproject.be.commaserver.common.exception.PageSizeOutOfBoundsException;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

@DisplayName("게시글 통합 테스트")
public class CommaIntegrationTest extends InitIntegrationTest {

    @Test
    @DisplayName("모든 게시글 조회 시 페이징을 적용해서 반환한다")
    void readAll_comma_pagination() {
        int pageSize = 2;
        int pageSize2 = 1;
        PageRequest pageRequest = PageRequest.of(0, pageSize);
        PageRequest pageRequest2 = PageRequest.of(0, pageSize2);

        List<CommaDetailResponse> commaDetailResponses = commaService.readAll(pageRequest);
        List<CommaDetailResponse> commaDetailResponses2 = commaService.readAll(pageRequest2);

        assertSoftly(softly -> {
            softly.assertThat(commaDetailResponses.size()).isEqualTo(2);
            softly.assertThat(commaDetailResponses2.size()).isEqualTo(1);
        });
    }

    @Test
    @DisplayName("100개 이상의 게시글 페이징 처리 요청 시 에러를 발생시킨다")
    void comma_page_size_out_of_bounds() {
        int pageSize = 101;
        PageRequest pageRequest = PageRequest.of(0, pageSize);

        assertThatThrownBy(() -> commaService.readAll(pageRequest))
            .isInstanceOf(PageSizeOutOfBoundsException.class);
    }
}
