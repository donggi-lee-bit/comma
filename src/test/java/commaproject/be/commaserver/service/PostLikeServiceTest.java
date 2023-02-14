package commaproject.be.commaserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.domain.user.User;
import commaproject.be.commaserver.repository.CommaRepository;
import commaproject.be.commaserver.repository.UserRepository;
import commaproject.be.commaserver.service.dto.PostLikeRequest;
import commaproject.be.commaserver.service.dto.PostLikeResponse;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class PostLikeServiceTest {

    @InjectMocks
    private PostLikeServiceImpl postLikeService;

    @Mock
    private CommaRepository commaRepository;

    @Mock
    private UserRepository userRepository;

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

        PostLikeResponse postLikeResponse = postLikeService.like(postLikeRequest, loginUserId, commaId);

        assertThat(postLikeResponse.getCommaId()).isEqualTo(commaId);
    }

    private User setUserData(Long userId) {
        User user = User.from("username1", "email1", "kakao@kakao.com");
        ReflectionTestUtils.setField(user, "id", userId);
        return user;
    }

    private Comma setCommaData(Long commaId, Long userId) {
        Comma comma = Comma.from("title1", "content1", "username1", userId);
        ReflectionTestUtils.setField(comma, "id", commaId);
        return comma;
    }
}
