package commaproject.be.commaserver.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import commaproject.be.commaserver.service.dto.CommaPaginatedResponse;
import commaproject.be.commaserver.service.dto.CommaSearchConditionRequest;
import commaproject.be.commaserver.service.dto.CommentDetailResponse;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

@DisplayName("회고 게시글 검색 조회 통합 테스트")
public class CommaSearchIntegrationTest extends InitIntegrationTest {

    @Test
    @DisplayName("특정 날짜에 작성한 회고 게시글을 조회하면 테스트가 성공한다")
    void search_by_date_condition_success() {
        CommaSearchConditionRequest commaSearchConditionRequest = new CommaSearchConditionRequest(LocalDate.now(), null);
        PageRequest pageRequest = PageRequest.of(0, 2);

        CommaPaginatedResponse commaPaginatedResponse = commaSearchService.searchByCondition(commaSearchConditionRequest, pageRequest);

        assertThat(commaPaginatedResponse.getCommaDetailResponses().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("특정 유저가 작성한 회고 게시글을 조회하면 테스트가 성공한다")
    void search_by_user_condition_success() {
        CommaSearchConditionRequest commaSearchConditionRequest = new CommaSearchConditionRequest(null, "donggi");
        PageRequest pageRequest = PageRequest.of(0, 2);

        CommaPaginatedResponse commaPaginatedResponse = commaSearchService.searchByCondition(commaSearchConditionRequest, pageRequest);
        String username = commaPaginatedResponse.getCommaDetailResponses().get(0).getUsername();

        assertSoftly(softly -> {
            softly.assertThat(username).isEqualTo("donggi");
            softly.assertThat(commaPaginatedResponse.getCommaDetailResponses().size()).isEqualTo(2);
        });
    }

    @Test
    @DisplayName("특정 날짜에 특정 유저가 작성한 회고 게시글을 조회하면 테스트가 성공한다")
    void search_by_user_date_condition_success() {
        CommaSearchConditionRequest commaSearchConditionRequest = new CommaSearchConditionRequest(LocalDate.now(), "donggi");
        PageRequest pageRequest = PageRequest.of(0, 2);

        CommaPaginatedResponse commaPaginatedResponse = commaSearchService.searchByCondition(commaSearchConditionRequest, pageRequest);
        String username = commaPaginatedResponse.getCommaDetailResponses().get(0).getUsername();

        assertSoftly(softly -> {
            softly.assertThat(username).isEqualTo("donggi");
            softly.assertThat(commaPaginatedResponse.getCommaDetailResponses().size()).isEqualTo(2);
        });
    }

    @Test
    @DisplayName("100개 이상의 회고 게시글 페이지 요청 시 100개로 요청하도록 한다")
    void comment_page_size_out_of_bounds() {
        int pageSize = 1000;
        PageRequest pageRequest = PageRequest.of(0, pageSize);
        CommaSearchConditionRequest commaSearchConditionRequest = new CommaSearchConditionRequest(LocalDate.now(), "donggi");

        CommaPaginatedResponse commaPaginatedResponse = commaSearchService.searchByCondition(commaSearchConditionRequest, pageRequest);
        assertThat(commaPaginatedResponse.getCommaDetailResponses().size()).isEqualTo(3);
    }
}
