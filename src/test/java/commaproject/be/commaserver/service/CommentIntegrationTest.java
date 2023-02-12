package commaproject.be.commaserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import commaproject.be.commaserver.common.exception.user.NotFoundUserException;
import commaproject.be.commaserver.domain.comment.Comment;
import commaproject.be.commaserver.service.dto.CommentDetailResponse;
import commaproject.be.commaserver.service.dto.CommentRequest;
import commaproject.be.commaserver.service.dto.CommentResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("댓글 서비스 통합 테스트")
class CommentIntegrationTest extends InitIntegrationTest {

    @Test
    @DisplayName("정상적인 요청이라면 댓글을 생성하고 테스트가 성공한다")
    void create_comment_success() {
        // given
        Long loginUserId = 1L;
        Long commaId = 1L;
        CommentRequest commentRequest = new CommentRequest("content1");

        // when
        CommentResponse commentResponse = commentService.create(loginUserId, commaId,
            commentRequest);

        // then
        assertThat(commentResponse.getId()).isEqualTo(4L);
    }

    @Test
    @DisplayName("허용되지 않은 유저가 댓글을 생성하면 예외를 발생시킨다")
    void un_authorized_user_create_comment() {
        // given
        Long unauthorizedUserId = Long.MAX_VALUE;
        Long commaId = 1L;
        CommentRequest commentRequest = new CommentRequest("content1");

        // when

        // then
        assertThatThrownBy(() -> commentService.create(unauthorizedUserId, commaId, commentRequest))
            .isInstanceOf(NotFoundUserException.class);
    }

    @Test
    @DisplayName("정상적인 요청이라면 댓글을 수정하고 테스트가 성공한다")
    void update_comment_success() {
        // given
        Long loginUserId = 1L;
        Long commaId = 1L;
        Long commentId = 1L;
        CommentRequest commentRequest = new CommentRequest("update content1");

        // when
        CommentDetailResponse updateCommentDetailResponse = commentService.update(loginUserId, commaId, commentId,
            commentRequest);

        // then
        assertThat(updateCommentDetailResponse.getId()).isEqualTo(1L);
        assertThat(updateCommentDetailResponse.getContent()).isEqualTo("update content1");
    }

    @Test
    @DisplayName("정상적인 요청이라면 댓글을 soft delete 하고 테스트가 성공한다")
    void soft_delete_comment_success() {
        // given
        Long loginUserId = 1L;
        Long commaId = 1L;
        Long commentId = 1L;

        // when
        Comment comment = commentService.delete(loginUserId, commaId, commentId);

        // then
        assertThat(comment.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("유효한 회고 게시글 id라면 해당 게시글의 모든 댓글을 조회하고 테스트가 성공한다")
    void read_All_comment_success() {
        // given
        Long commaId = 1L;

        // when
        List<CommentDetailResponse> commentDetailResponses = commentService.readAll(commaId);

        // then
        assertThat(commentDetailResponses.size()).isEqualTo(3);
    }
}
