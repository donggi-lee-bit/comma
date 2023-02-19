package commaproject.be.commaserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import commaproject.be.commaserver.common.exception.postlike.AlreadyPostLikeException;
import commaproject.be.commaserver.common.exception.postlike.AlreadyPostUnlikeException;
import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.domain.like.Like;
import commaproject.be.commaserver.domain.user.User;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PostLikeServiceTest extends InitServiceTest {

    @Test
    @DisplayName("로그인한 사용자가 게시글 좋아요를 하면 테스트가 성공한다")
    void valid_user_click_like() {
        Long commaId = 1L;
        Long loginUserId = 1L;
        User user = setUserData(loginUserId);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Comma comma = setCommaData(commaId, loginUserId);
        when(commaRepository.findById(commaId)).thenReturn(Optional.of(comma));
        Like like = Like.of(loginUserId, commaId);
        when(postLikeRepository.findByIdAndCommaId(loginUserId, commaId)).thenReturn(
            Optional.of(like));

        postLikeService.like(loginUserId, commaId);

        assertThat(like.isLikeStatus()).isTrue();
    }

    @Test
    @DisplayName("사용자가 좋아요 한 게시글을 다시 좋아요를 하면 예외를 발생시킨다")
    void valid_user_duplicate_click_like() {
        Long commaId = 1L;
        Long loginUserId = 1L;
        User user = setUserData(loginUserId);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Comma comma = setCommaData(commaId, loginUserId);
        when(commaRepository.findById(commaId)).thenReturn(Optional.of(comma));
        Like like = Like.of(loginUserId, commaId);
        like.clickPostLike(true);
        when(postLikeRepository.findByIdAndCommaId(loginUserId, commaId)).thenReturn(
            Optional.of(like));

        assertThatThrownBy(() -> postLikeService.like(loginUserId, commaId))
            .isInstanceOf(AlreadyPostLikeException.class);
    }

    @Test
    @DisplayName("로그인한 사용자가 게시글 좋아요 취소를 하면 테스트가 성공한다")
    void valid_user_click_unlike() {
        Long commaId = 1L;
        Long loginUserId = 1L;
        User user = setUserData(loginUserId);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Comma comma = setCommaData(commaId, loginUserId);
        when(commaRepository.findById(commaId)).thenReturn(Optional.of(comma));
        Like like = Like.of(loginUserId, commaId);
        like.clickPostLike(true);
        when(postLikeRepository.findByIdAndCommaId(loginUserId, commaId)).thenReturn(
            Optional.of(like));

        postLikeService.unlike(loginUserId, commaId);

        assertThat(like.isLikeStatus()).isFalse();
    }

    @Test
    @DisplayName("사용자가 좋아요하지 않은 게시글을 좋아요 취소 요청을 하면 예외를 발생시킨다")
    void valid_user_duplicate_click_unlike() {
        Long commaId = 1L;
        Long loginUserId = 1L;
        User user = setUserData(loginUserId);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Comma comma = setCommaData(commaId, loginUserId);
        when(commaRepository.findById(commaId)).thenReturn(Optional.of(comma));
        Like like = Like.of(loginUserId, commaId);
        when(postLikeRepository.findByIdAndCommaId(loginUserId, commaId)).thenReturn(
            Optional.of(like));

        assertThatThrownBy(() -> postLikeService.unlike(loginUserId, commaId))
            .isInstanceOf(AlreadyPostUnlikeException.class);
    }
}
