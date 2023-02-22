package commaproject.be.commaserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import commaproject.be.commaserver.domain.comment.Comment;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommaSearchConditionRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("회고 게시글 검색 mock 을 이용한 테스트")
class CommaSearchServiceTest extends InitServiceTest {

    @Test
    @DisplayName("특정 날짜에 작성한 회고 글을 조회하고 테스트가 성공한다")
    void search_by_date_condition_success() {
        Long commaId = 1L;
        CommaSearchConditionRequest request = new CommaSearchConditionRequest(expectedLocalDateTime, "donggi");
        LocalDateTime startDate = request.getDate();
        LocalDateTime endDate = startDate.plusDays(1L);
        List<Comment> comments = new ArrayList<>();
        when(commentRepository.findAllByCommaId(commaId)).thenReturn(Optional.of(comments));
        when(commaRepository.searchByDateCondition(startDate, endDate)).thenReturn(setCommasData()); // Querydsl 로 검색 조건을 만들어서 게시글들을 조회하는 메서드 실행

        List<CommaDetailResponse> commaDetailResponses = commaSearchService.searchByCondition(
            request);

        assertThat(commaDetailResponses.size()).isEqualTo(3);
    }
}
