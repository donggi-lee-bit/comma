package commaproject.be.commaserver.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommaSearchConditionRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("회고 게시글 검색 조회 통합 테스트")
public class CommaSearchIntegrationTest extends InitIntegrationTest {

    @Test
    @DisplayName("특정 날짜에 작성한 회고 게시글을 조회하면 테스트가 성공한다")
    void search_by_date_condition_success() {
        CommaSearchConditionRequest commaSearchConditionRequest = new CommaSearchConditionRequest(
            LocalDateTime.now().minusMinutes(30L), null
        );

        List<CommaDetailResponse> commaDetailResponses = commaSearchService.searchByDateCondition(commaSearchConditionRequest);

        assertThat(commaDetailResponses.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("특정 유저가 작성한 회고 게시글을 조회하면 테스트가 성공한다")
    void search_by_user_condition_success() {
        CommaSearchConditionRequest commaSearchConditionRequest = new CommaSearchConditionRequest(
            null, "donggi"
        );

        List<CommaDetailResponse> commaDetailResponses = commaSearchService.searchByUserCondition(commaSearchConditionRequest);
        String username = commaDetailResponses.get(0).getUsername();

        assertSoftly(softly -> {
            softly.assertThat(username).isEqualTo("donggi");
            softly.assertThat(commaDetailResponses.size()).isEqualTo(3);
        });
    }

    @Test
    @DisplayName("특정 날짜에 특정 유저가 작성한 회고 게시글을 조회하면 테스트가 성공한다")
    void search_by_user_date_condition_success() {
        CommaSearchConditionRequest commaSearchConditionRequest = new CommaSearchConditionRequest(
            LocalDateTime.now().minusMinutes(30L), "donggi"
        );

        List<CommaDetailResponse> commaDetailResponses = commaSearchService.searchByUserDateCondition(commaSearchConditionRequest);
        String username = commaDetailResponses.get(0).getUsername();

        assertSoftly(softly -> {
            softly.assertThat(username).isEqualTo("donggi");
            softly.assertThat(commaDetailResponses.size()).isEqualTo(3);
        });
    }
}
