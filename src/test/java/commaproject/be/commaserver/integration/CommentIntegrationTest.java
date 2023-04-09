package commaproject.be.commaserver.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import commaproject.be.commaserver.common.exception.PageSizeOutOfBoundsException;
import commaproject.be.commaserver.common.exception.comma.NotFoundCommaException;
import commaproject.be.commaserver.common.exception.comment.NotFoundCommentException;
import commaproject.be.commaserver.common.exception.user.NotFoundUserException;
import commaproject.be.commaserver.common.exception.user.UnAuthorizedUserException;
import commaproject.be.commaserver.domain.comment.Comment;
import commaproject.be.commaserver.service.dto.CommentDetailResponse;
import commaproject.be.commaserver.service.dto.CommentRequest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

@DisplayName("댓글 서비스 통합 테스트")
class CommentIntegrationTest extends InitIntegrationTest {

    @Test
    @DisplayName("정상적인 요청이라면 댓글을 생성하고 테스트가 성공한다")
    void create_comment_success() {
        Long loginUserId = 1L;
        Long commaId = 1L;
        CommentRequest commentRequest = new CommentRequest("content1");

        Comment comment = commentService.create(loginUserId, commaId,
            commentRequest);

        assertThat(comment.getContent()).isEqualTo("content1");
    }

    @Test
    @DisplayName("허용되지 않은 유저가 댓글을 생성하면 예외를 발생시킨다")
    void un_authorized_user_create_comment() {
        Long unauthorizedUserId = Long.MAX_VALUE;
        Long commaId = 1L;
        CommentRequest commentRequest = new CommentRequest("content1");

        assertThatThrownBy(() -> commentService.create(unauthorizedUserId, commaId, commentRequest))
            .isInstanceOf(NotFoundUserException.class);
    }

    @Test
    @DisplayName("유효하지 않은 회고 게시글에 댓글을 생성하면 예외를 발생시킨다")
    void invalid_comma_create_comment() {
        Long userId = 1L;
        Long invalidCommaId = Long.MAX_VALUE;
        CommentRequest commentRequest = new CommentRequest("content1");

        assertThatThrownBy(() -> commentService.create(userId, invalidCommaId, commentRequest))
            .isInstanceOf(NotFoundCommaException.class);
    }

    @Test
    @DisplayName("정상적인 요청이라면 댓글을 수정하고 테스트가 성공한다")
    void update_comment_success() {
        Long loginUserId = 1L;
        Long commaId = 1L;
        Long commentId = 1L;
        CommentRequest commentRequest = new CommentRequest("update content1");

        CommentDetailResponse updateCommentDetailResponse = commentService.update(loginUserId, commaId, commentId,
            commentRequest);

        assertSoftly(softly -> {
            softly.assertThat(updateCommentDetailResponse.getId()).isEqualTo(1L);
            softly.assertThat(updateCommentDetailResponse.getContent()).isEqualTo("update content1");
        });
    }

    @Test
    @DisplayName("댓글을 쓴 유저가 아닌 유저가 댓글을 수정하려고 하면 예외를 발생시킨다")
    void un_authorized_user_update_comment() {
        Long unauthorizedUserId = testData.testUser.getId();
        Long commaId = 1L;
        Long commentId = 2L;
        CommentRequest commentRequest = new CommentRequest("update content1");

        assertThatThrownBy(() -> commentService.update(unauthorizedUserId, commaId, commentId, commentRequest))
            .isInstanceOf(UnAuthorizedUserException.class);
    }

    @Test
    @DisplayName("존재하지 않는 게시글의 댓글을 수정하려고 하면 예외를 발생시킨다")
    void invalid_comma_update_comment() {
        Long userId = testData.testUser.getId();
        Long commaId = Long.MAX_VALUE;
        Long commentId = 1L;
        CommentRequest commentRequest = new CommentRequest("update content1");

        assertThatThrownBy(() -> commentService.update(userId, commaId, commentId, commentRequest))
            .isInstanceOf(NotFoundCommaException.class);
    }

    @Test
    @DisplayName("정상적인 요청이라면 댓글을 soft delete 하고 테스트가 성공한다")
    void soft_delete_comment_success() {
        Long loginUserId = 1L;
        Long commaId = 1L;
        Long commentId = 1L;

        Comment comment = commentService.delete(loginUserId, commaId, commentId);

        assertThat(comment.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("정상적인 요청이라면 댓글을 soft delete 하고 해당 댓글을 조회하면 비어있는 객체를 반환한다")
    void soft_delete_comment_success_2() {
        Long loginUserId = 1L;
        Long commaId = 1L;
        Long commentId = 1L;

        Comment comment = commentService.delete(loginUserId, commaId, commentId);

        assertThat(commentRepository.findById(comment.getId())).isEqualTo(Optional.empty());
    }

    @Test
    @DisplayName("권한이 없는 유저가 댓글을 삭제하려고 하면 예외를 발생시킨다")
    void un_authorized_user_soft_delete_comment() {
        Long loginUserId = 1L;
        Long commaId = 1L;
        Long commentId = 2L;

        assertThatThrownBy(() -> commentService.delete(loginUserId, commaId, commentId))
            .isInstanceOf(UnAuthorizedUserException.class);
    }

    @Test
    @DisplayName("유효한 회고 게시글이면 해당 게시글의 모든 댓글을 조회하고 테스트가 성공한다")
    void read_All_comment_success() {
        Long commaId = 1L;
        PageRequest commentPageRequest = PageRequest.of(0, 10);

        List<CommentDetailResponse> commentDetailResponses = commentService.readAll(commaId, commentPageRequest);

        assertThat(commentDetailResponses.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("존재하지 않는 회고 게시글의 댓글을 조회하려고 하면 예외를 발생시킨다")
    void invalid_comma_read_All_comment() {
        Long commaId = Long.MAX_VALUE;
        PageRequest commentPageRequest = PageRequest.of(0, 10);

        assertThatThrownBy(() -> commentService.readAll(commaId, commentPageRequest))
            .isInstanceOf(NotFoundCommaException.class);
    }

    @Test
    @DisplayName("유효한 회고 게시글의 유효한 댓글 id면 댓글을 조회하고 테스트가 성공한다")
    void read_one_comment_success() {
        Long commaId = 1L;
        Long commentId = 1L;

        CommentDetailResponse commentDetailResponse = commentService.readOne(commaId, commentId);

        assertThat(commentDetailResponse.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("존재하지 않는 회고 게시글의 댓글을 조회하려고 하면 예외를 발생시킨다")
    void invalid_comma_read_one_comment() {
        Long commaId = Long.MAX_VALUE;
        Long commentId = 1L;

        assertThatThrownBy(() -> commentService.readOne(commaId, commentId))
            .isInstanceOf(NotFoundCommaException.class);
    }

    @Test
    @DisplayName("존재하지 않는 회고 댓글을 조회하려고 하면 예외를 발생시킨다")
    void invalid_comment_read_one() {
        Long commaId = 1L;
        Long commentId = Long.MAX_VALUE;

        assertThatThrownBy(() -> commentService.readOne(commaId, commentId))
            .isInstanceOf(NotFoundCommentException.class);
    }

    @Test
    @DisplayName("10개 이상의 댓글 페이징 처리 요청 시 에러를 발생시킨다")
    void comment_page_size_out_of_bounds() {
        Long commaId = 1L;
        int pageSize = 11;
        PageRequest pageRequest = PageRequest.of(0, pageSize);

        assertThatThrownBy(() -> commentService.readAll(commaId, pageRequest))
            .isInstanceOf(PageSizeOutOfBoundsException.class);
    }
}
