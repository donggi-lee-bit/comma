package commaproject.be.commaserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.when;

import commaproject.be.commaserver.common.exception.comma.NotFoundCommaException;
import commaproject.be.commaserver.common.exception.postlike.AlreadyPostLikeException;
import commaproject.be.commaserver.common.exception.postlike.AlreadyPostUnlikeException;
import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.domain.user.User;
import commaproject.be.commaserver.service.dto.PostLikeRequest;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostLikeServiceTest extends InitServiceTest{

    @Test
    @DisplayName("로그인한 사용자가 게시글 좋아요를 하면 테스트가 성공한다")
    void valid_user_click_like() {
        PostLikeRequest postLikeRequest = new PostLikeRequest(true);
        Long commaId = 1L;
        Long loginUserId = 1L;
        User user = setUserData(loginUserId);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Comma comma = setCommaData(commaId, loginUserId);
        when(commaRepository.findById(commaId)).thenReturn(Optional.of(comma));

        postLikeService.like(postLikeRequest, loginUserId, commaId);

        assertSoftly(softly -> {
            softly.assertThat(user.getLikes().size()).isEqualTo(1);
            softly.assertThat(comma.getLikeUsers().size()).isEqualTo(1);
        });
    }

    @Test
    @DisplayName("사용자가 좋아요 한 게시글을 다시 좋아요를 하면 예외를 발생시킨다")
    void valid_user_duplicate_click_like() {
        PostLikeRequest postLikeRequest = new PostLikeRequest(true);
        Long commaId = 1L;
        Long loginUserId = 1L;
        User user = setUserData(loginUserId);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Comma comma = setCommaData(commaId, loginUserId);
        when(commaRepository.findById(commaId)).thenReturn(Optional.of(comma));

        user.add(commaId);
        comma.add(loginUserId);

        assertThatThrownBy(() -> postLikeService.like(postLikeRequest, loginUserId, commaId))
            .isInstanceOf(AlreadyPostLikeException.class);
    }

    @Test
    @DisplayName("로그인한 사용자가 게시글 좋아요 취소를 하면 테스트가 성공한다")
    void valid_user_click_unlike() {
        PostLikeRequest postLikeRequest = new PostLikeRequest(true);
        Long commaId = 1L;
        Long loginUserId = 1L;
        User user = setUserData(loginUserId);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Comma comma = setCommaData(commaId, loginUserId);
        when(commaRepository.findById(commaId)).thenReturn(Optional.of(comma));
        user.add(commaId);
        comma.add(loginUserId);

        postLikeService.unlike(postLikeRequest, loginUserId, commaId);

        assertSoftly(softly -> {
            softly.assertThat(user.getLikes().size()).isEqualTo(0);
            softly.assertThat(comma.getLikeUsers().size()).isEqualTo(0);
        });
    }

    @Test
    @DisplayName("사용자가 좋아요하지 않은 게시글을 좋아요 취소 요청을 하면 예외를 발생시킨다")
    void valid_user_duplicate_click_unlike() {
        PostLikeRequest postLikeRequest = new PostLikeRequest(true);
        Long commaId = 1L;
        Long loginUserId = 1L;
        User user = setUserData(loginUserId);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Comma comma = setCommaData(commaId, loginUserId);
        when(commaRepository.findById(commaId)).thenReturn(Optional.of(comma));

        assertThatThrownBy(() -> postLikeService.unlike(postLikeRequest, loginUserId, commaId))
            .isInstanceOf(AlreadyPostUnlikeException.class);
    }

    @Test
    @DisplayName("특정 게시글 좋아요 개수를 조회하고 테스트가 성공한다")
    void read_post_like_count() {
        PostLikeRequest postLikeRequest = new PostLikeRequest(true);
        Long commaId = 1L;
        Long loginUserId = 1L;
        User user = setUserData(loginUserId);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Long loginUserId2 = 2L;
        User user2 = setUserData(loginUserId2);
        when(userRepository.findById(user2.getId())).thenReturn(Optional.of(user2));
        Comma comma = setCommaData(commaId, loginUserId);
        when(commaRepository.findById(commaId)).thenReturn(Optional.of(comma));
        postLikeService.like(postLikeRequest, loginUserId, commaId);
        postLikeService.like(postLikeRequest, loginUserId2, commaId);

        int postLikeCount = postLikeService.readPostLikeCount(commaId);

        assertThat(postLikeCount).isEqualTo(2);
    }

    @Test
    @DisplayName("유효하지 않은 게시글 id로 게시글의 좋아요 개수를 조회하면 예외를 발생시킨다")
    void read_post_invalid_like_count() {
        Long commaId = Long.MAX_VALUE;

        assertThatThrownBy(() -> postLikeService.readPostLikeCount(commaId))
            .isInstanceOf(NotFoundCommaException.class);
    }
}
