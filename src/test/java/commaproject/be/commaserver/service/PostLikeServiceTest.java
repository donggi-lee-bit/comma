package commaproject.be.commaserver.service;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.Mockito.when;

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
}
