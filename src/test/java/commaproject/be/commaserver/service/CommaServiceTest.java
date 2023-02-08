package commaproject.be.commaserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import commaproject.be.commaserver.common.exception.user.UnAuthorizedUserException;
import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.domain.user.User;
import commaproject.be.commaserver.repository.CommaRepository;
import commaproject.be.commaserver.repository.UserRepository;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommaRequest;
import commaproject.be.commaserver.service.dto.CommaResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommaServiceTest {

    @InjectMocks
    private CommaServiceImpl commaService;

    @Mock
    private CommaRepository commaRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("회고를 조회하면 DB에 저장된 모든 회고를 조회하고 테스트가 성공한다")
    void findAll() {
        // given
        List<Comma> commas = setCommaData();
        when(commaRepository.findAll()).thenReturn(commas);

        // when
        List<CommaDetailResponse> commaDetailResponsesExpected = commaService.readAll();

        // then
        assertThat(commaDetailResponsesExpected.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("유효한 commaId로 회고를 조회하면 테스트가 성공한다")
    void valid_comma_id_find_comma() {
        // given
        Long commaId = 1L;
        Long userId = 1L;
        Optional<Comma> comma = Optional.of(Comma.from("title1", "content1", "username1", userId));
        when(commaRepository.findById(commaId)).thenReturn(comma);

        // when
        CommaDetailResponse commaDetailResponse = commaService.readOne(commaId);

        // then
        assertThat(commaDetailResponse).isNotNull();
        assertThat(commaDetailResponse.getTitle()).isEqualTo("title1");
    }

    @Test
    @DisplayName("로그인한 유저가 작성한 회고를 유효한 commaId로 접근하여 수정하면 테스트가 성공한다")
    void update_comma() {
        // given
        Long commaId = 1L;
        Long userId = 1L;

        Comma comma = new Comma(commaId, "title1", "content1", "username1", userId);
        when(commaRepository.findById(commaId)).thenReturn(Optional.of(comma));

        User user = new User(userId, "username1", "email1", "kakao@kakao.com");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        // when
        CommaRequest commaRequest = new CommaRequest("title1", "update content1");
        CommaDetailResponse updateCommaDetailResponse = commaService.update(userId, commaId, commaRequest);

        // then
        assertThat(updateCommaDetailResponse.getContent()).isEqualTo("update content1");
    }

    @Test
    @DisplayName("허용되지 않은 유저가 게시글을 접근하여 수정하면 예외를 발생시킨다")
    void un_authorized_user_update_comma() {
        // given
        Long commaId = 1L;
        Long userId = 1L;
        Long unauthorizedUserId = Long.MAX_VALUE;

        Comma comma = new Comma(commaId, "title1", "content1", "username1", userId);
        when(commaRepository.findById(commaId)).thenReturn(Optional.of(comma));

        User user = new User(unauthorizedUserId, "username1", "email1", "kakao@kakao.com");
        when(userRepository.findById(unauthorizedUserId)).thenReturn(Optional.of(user));

        // when
        CommaRequest commaRequest = new CommaRequest("title1", "update content1");

        // then
        assertThatThrownBy(() -> commaService.update(unauthorizedUserId, commaId, commaRequest))
            .isInstanceOf(UnAuthorizedUserException.class);
    }

    @Test
    @DisplayName("로그인한 유저가 작성한 회고를 저장하면 테스트가 성공한다")
    void save_comma() {
        // given
        Long commaId = 1L;
        Long userId = 1L;

        User user = new User(userId, "username1", "email1", "kakao@kakao.com");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Comma comma = new Comma(commaId, "title1", "content1", "username1", userId);
        when(commaRepository.save(any(Comma.class))).thenReturn(comma);

        // when
        CommaResponse saveCommaResponse = commaService.create(userId,
            new CommaRequest("title1", "content1"));

        // then
        assertThat(saveCommaResponse).isNotNull();
    }

    @Test
    @DisplayName("로그인한 유저가 작성한 글을 유효한 commaId로 조회하여 soft delete 하면 테스트가 성공한다")
    void remove_comma() {
        // given
        Long commaId = 1L;
        Long userId = 1L;

        User user = new User(userId, "username1", "email1", "kakao@kakao.com");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Optional<Comma> comma = Optional.of(Comma.from("title1", "content1", "username1", userId));
        when(commaRepository.findById(commaId)).thenReturn(comma);

        // when
        CommaResponse removeComma = commaService.remove(userId, commaId);

        // then
        verify(commaRepository, times(1)).delete(comma.get());
        assertThat(removeComma).isNotNull();
    }

    @Test
    @DisplayName("권한이 없는 유저가 게시글을 삭제하면 예외를 발생시킨다")
    void un_authorized_user_remove_comma() {
        // given
        Long commaId = 1L;
        Long userId = 1L;
        Long unauthorizedUserId = Long.MAX_VALUE;

        User user = new User(unauthorizedUserId, "username1", "email1", "kakao@kakao.com");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        Optional<Comma> comma = Optional.of(Comma.from("title1", "content1", "username1", userId));
        when(commaRepository.findById(commaId)).thenReturn(comma);

        // when

        // then
        assertThatThrownBy(() -> commaService.remove(unauthorizedUserId, commaId))
            .isInstanceOf(UnAuthorizedUserException.class);
    }

    private static List<Comma> setCommaData() {
        List<Comma> commas = new ArrayList<>();
        Long userId = 1L;
        for (int i = 1; i <= 3; i++) {
            commas.add(Comma.from("title1", "content1", "username1", userId));
        }
        return commas;
    }
}
