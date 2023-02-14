package commaproject.be.commaserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import commaproject.be.commaserver.common.exception.user.UnAuthorizedUserException;
import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.domain.user.User;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommaRequest;
import commaproject.be.commaserver.service.dto.CommaResponse;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommaServiceTest extends InitServiceTest{

    @Test
    @DisplayName("회고를 조회하면 DB에 저장된 모든 회고를 조회하고 테스트가 성공한다")
    void findAll() {
        List<Comma> commas = setCommasData();
        when(commaRepository.findAll()).thenReturn(commas);

        List<CommaDetailResponse> commaDetailResponsesExpected = commaService.readAll();

        assertThat(commaDetailResponsesExpected.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("유효한 commaId로 회고를 조회하면 테스트가 성공한다")
    void valid_comma_id_find_comma() {
        Long commaId = 1L;
        Long userId = 1L;
        Optional<Comma> comma = Optional.of(Comma.from("title1", "content1", "username1", userId));
        when(commaRepository.findById(commaId)).thenReturn(comma);

        CommaDetailResponse commaDetailResponse = commaService.readOne(commaId);

        assertSoftly(softly -> {
            softly.assertThat(commaDetailResponse).isNotNull();
            softly.assertThat(commaDetailResponse.getTitle()).isEqualTo("title1");
        });
    }

    @Test
    @DisplayName("로그인한 유저가 작성한 회고를 유효한 commaId로 접근하여 수정하면 테스트가 성공한다")
    void update_comma() {
        Long commaId = 1L;
        Long userId = 1L;
        Comma comma = setCommaData(commaId, userId);
        when(commaRepository.findById(commaId)).thenReturn(Optional.of(comma));
        User user = setUserData(userId);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        CommaRequest commaRequest = new CommaRequest("title1", "update content1");
        CommaDetailResponse updateCommaDetailResponse = commaService.update(userId, commaId, commaRequest);

        assertThat(updateCommaDetailResponse.getContent()).isEqualTo("update content1");
    }

    @Test
    @DisplayName("허용되지 않은 유저가 게시글을 접근하여 수정하면 예외를 발생시킨다")
    void un_authorized_user_update_comma() {
        Long commaId = 1L;
        Long userId = 1L;
        Long unauthorizedUserId = Long.MAX_VALUE;
        Comma comma = setCommaData(commaId, userId);
        when(commaRepository.findById(commaId)).thenReturn(Optional.of(comma));
        User user = setUserData(unauthorizedUserId);
        when(userRepository.findById(unauthorizedUserId)).thenReturn(Optional.of(user));

        CommaRequest commaRequest = new CommaRequest("title1", "update content1");

        assertThatThrownBy(() -> commaService.update(unauthorizedUserId, commaId, commaRequest))
            .isInstanceOf(UnAuthorizedUserException.class);
    }

    @Test
    @DisplayName("로그인한 유저가 작성한 회고를 저장하면 테스트가 성공한다")
    void save_comma() {
        Long commaId = 1L;
        Long userId = 1L;
        User user = setUserData(userId);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Comma comma = setCommaData(commaId, userId);
        when(commaRepository.save(any(Comma.class))).thenReturn(comma);

        CommaResponse saveCommaResponse = commaService.create(userId,
            new CommaRequest("title1", "content1"));

        assertThat(saveCommaResponse.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("로그인한 유저가 작성한 글을 유효한 commaId로 조회하여 soft delete 하면 테스트가 성공한다")
    void remove_comma() {
        Long commaId = 1L;
        Long userId = 1L;
        User user = setUserData(userId);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Comma comma = setCommaData(commaId, userId);
        when(commaRepository.findById(commaId)).thenReturn(Optional.of(comma));

        Comma removeComma = commaService.remove(userId, commaId);

        assertThat(removeComma.isDeleted()).isTrue();
    }

    @Test
    @DisplayName("권한이 없는 유저가 게시글을 삭제하면 예외를 발생시킨다")
    void un_authorized_user_remove_comma() {
        Long commaId = 1L;
        Long userId = 1L;
        Long unauthorizedUserId = Long.MAX_VALUE;
        User user = setUserData(unauthorizedUserId);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Comma comma = setCommaData(commaId, userId);
        when(commaRepository.findById(commaId)).thenReturn(Optional.of(comma));

        assertThatThrownBy(() -> commaService.remove(unauthorizedUserId, commaId))
            .isInstanceOf(UnAuthorizedUserException.class);
    }


}
