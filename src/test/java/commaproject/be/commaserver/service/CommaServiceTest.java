package commaproject.be.commaserver.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.repository.CommaRepository;
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

    @Test
    @DisplayName("DB에 저장된 회고를 조회하면 모든 회고를 조회하고 테스트가 성공한다")
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
        // todo id를 조회하고자 했는데 comma를 jpa로 저장하지 않아서 id 생성이 안되는걸까?
        assertThat(commaDetailResponse).isNotNull();
        assertThat(commaDetailResponse.getTitle()).isEqualTo("title1");
    }

    @Test
    @DisplayName("유효한 commaId로 회고를 조회하여 수정한 데이터를 확인할 수 있으면 테스트가 성공한다")
    void update_comma() {
        // given
        Long commaId = 1L;
        Long userId = 1L;
        Optional<Comma> comma = Optional.of(Comma.from("title1", "content1", "username1", userId));
        when(commaRepository.findById(commaId)).thenReturn(comma);

        // when
        CommaRequest commaRequest = new CommaRequest("title1", "update content1", "username", userId
        );
        CommaDetailResponse updateCommaDetailResponse = commaService.update(commaId, commaRequest);

        // then
        assertThat(updateCommaDetailResponse.getContent()).isEqualTo("update content1");
    }

    @Test
    @DisplayName("회고 데이터가 저장되면 테스트가 성공한다")
    void save_comma() {
        // given
        Long userId = 1L;
        Comma comma = Comma.from("title1", "content1", "username", userId);
        when(commaRepository.save(any(Comma.class))).thenReturn(comma);

        // when
        CommaResponse saveCommaResponse = commaService.create(
            new CommaRequest("title1", "content1", "username", userId));

        // then
        assertThat(saveCommaResponse).isNotNull();
    }

    @Test
    @DisplayName("유효한 commaId로 회고를 조회하여 soft delete를 하면 테스트가 성공한다")
    void remove_comma() {
        // given
        Long userId = 1L;
        Comma comma = Comma.from("title1", "content1", "username", userId);
        when(commaRepository.save(any(Comma.class))).thenReturn(comma);


        // when
        Long commaId = 1L;
        commaService.remove(commaId);

        // then
        verify(commaRepository, times(1)).save(any(Comma.class));
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
