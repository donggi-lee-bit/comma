package commaproject.be.commaserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import commaproject.be.commaserver.common.exception.user.UnAuthorizedUserException;
import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.domain.comment.Comment;
import commaproject.be.commaserver.domain.user.User;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommaRequest;
import commaproject.be.commaserver.service.dto.CommaResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

class CommaServiceTest extends InitServiceTest {

    @Test
    @DisplayName("회고를 조회하면 DB에 저장된 모든 회고를 조회하고 테스트가 성공한다")
    void findAll() {
        Long commaId = 1L;
        Page<Comma> commas = setCommasData();
        List<Comment> comments = new ArrayList<>();
        // mocking 하고 있기 때문에 실제로 해당 테스트 메서드에서는 페이징 처리가 되고 있진 않음
        PageRequest pageRequest = PageRequest.of(0, 2);
        when(commaRepository.findAll(pageRequest)).thenReturn(commas);
        when(commentRepository.findAllByCommaId(commaId)).thenReturn(comments);

        List<CommaDetailResponse> commaDetailResponsesExpected = commaService.readAll(pageRequest);

        assertThat(commaDetailResponsesExpected.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("유효한 commaId로 회고를 조회하면 테스트가 성공한다")
    void valid_comma_id_find_comma() {
        Long commaId = 1L;
        Long userId = 1L;
        Comma comma = setCommaData(commaId, userId);
        List<Comment> comments = setCommentsData(commaId, userId);
        when(commaRepository.findById(commaId)).thenReturn(Optional.of(comma));
        when(postLikeRepository.countLikeByCommaIdAndLikeStatus(commaId, true)).thenReturn(1L);
        when(commentRepository.findAllByCommaId(commaId)).thenReturn(comments);

        CommaDetailResponse commaDetailResponse = commaService.readOne(commaId);

        assertSoftly(softly -> {
            softly.assertThat(commaDetailResponse.getId()).isEqualTo(1L);
            softly.assertThat(commaDetailResponse.getTitle()).isEqualTo("title1");
            softly.assertThat(commaDetailResponse.getPostLikeCount()).isEqualTo(1);
            softly.assertThat(commaDetailResponse.getComments().size()).isEqualTo(3);
        });
    }

    @Test
    @DisplayName("로그인한 유저가 작성한 회고를 유효한 commaId로 접근하여 수정하면 테스트가 성공한다")
    void update_comma() {
        Long commaId = 1L;
        Long userId = 1L;
        Comma comma = setCommaData(commaId, userId);
        User user = setUserData(userId);
        List<Comment> comments = setCommentsData(commaId, userId);
        when(commaRepository.findById(commaId)).thenReturn(Optional.of(comma));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(commentRepository.findAllByCommaId(commaId)).thenReturn(comments);

        CommaRequest commaRequest = new CommaRequest("title1", "update content1");
        CommaDetailResponse updateCommaDetailResponse = commaService.update(userId, commaId, commaRequest);

        assertSoftly(softly -> {
            softly.assertThat(updateCommaDetailResponse.getId()).isEqualTo(1L);
            softly.assertThat(updateCommaDetailResponse.getContent()).isEqualTo("update content1");
        });
    }

    @Test
    @DisplayName("허용되지 않은 유저가 게시글을 접근하여 수정하면 예외를 발생시킨다")
    void un_authorized_user_update_comma() {
        Long commaId = 1L;
        Long userId = 1L;
        Long unauthorizedUserId = Long.MAX_VALUE;
        Comma comma = setCommaData(commaId, userId);
        User user = setUserData(unauthorizedUserId);
        when(commaRepository.findById(commaId)).thenReturn(Optional.of(comma));
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
        Comma comma = setCommaData(commaId, userId);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
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
        Comma comma = setCommaData(commaId, userId);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
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
        Comma comma = setCommaData(commaId, userId);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(commaRepository.findById(commaId)).thenReturn(Optional.of(comma));

        assertThatThrownBy(() -> commaService.remove(unauthorizedUserId, commaId))
            .isInstanceOf(UnAuthorizedUserException.class);
    }


}
