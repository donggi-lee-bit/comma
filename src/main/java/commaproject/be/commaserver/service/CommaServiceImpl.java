package commaproject.be.commaserver.service;

import commaproject.be.commaserver.common.exception.UnAuthorizedUserException;
import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.domain.user.User;
import commaproject.be.commaserver.repository.CommaRepository;
import commaproject.be.commaserver.repository.UserRepository;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommaRequest;
import commaproject.be.commaserver.service.dto.CommaResponse;
import commaproject.be.commaserver.service.dto.CommentDetailResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommaServiceImpl implements CommaService {

    private final CommaRepository commaRepository;
    private final UserRepository userRepository;

    @Override
    public CommaDetailResponse readOne(Long commaId) {
        // user, like, comment 도메인 개발 예정
        Comma findComma = commaRepository.findById(commaId)
            .orElseThrow(NoSuchElementException::new);

        Long userId = 1L;
        int likeCount = 1;
        List<CommentDetailResponse> comments = new ArrayList<>();

        return new CommaDetailResponse(
            findComma.getId(),
            findComma.getTitle(),
            findComma.getContent(),
            findComma.getUsername(),
            userId,
            findComma.getCreatedAt(),
            likeCount,
            comments
            );
    }

    @Override
    public List<CommaDetailResponse> readAll() {
        List<Comma> commas = commaRepository.findAll();

        // todo likeCount, comments 상수 입력 상태

        return commas.stream()
            .map(comma -> new CommaDetailResponse(
                comma.getId(), comma.getTitle(), comma.getContent(),
                comma.getUsername(), comma.getUserId(), comma.getCreatedAt(),
                0,
                new ArrayList<>()))
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public CommaResponse create(Long loginUserId, CommaRequest commaRequest) {
        // todo Exception 재정의
        User user = userRepository.findById(loginUserId)
            .orElseThrow(NoSuchElementException::new);

        Comma saveComma = commaRepository.save(
            Comma.from(commaRequest.getTitle(), commaRequest.getContent(),
                user.getUsername(), user.getId()));

        return new CommaResponse(saveComma.getId());
    }

    @Override
    @Transactional
    public CommaDetailResponse update(Long loginUserId, Long commaId, CommaRequest commaRequest) {
        // todo likeCount, comments 상수 입력 상태
        Comma comma = commaRepository.findById(commaId)
            .orElseThrow(NoSuchElementException::new);

        User user = userRepository.findById(loginUserId)
            .orElseThrow(NoSuchElementException::new);

        validateUpdateComma(loginUserId, comma.getUserId());

        Comma updateComma = comma.update(
            commaRequest.getTitle(),
            commaRequest.getContent(),
            user.getUsername(),
            user.getId());

        return new CommaDetailResponse(updateComma.getId(), updateComma.getTitle(),
            updateComma.getContent(), updateComma.getUsername(), updateComma.getUserId(),
            updateComma.getCreatedAt(), 0, new ArrayList<>());
    }

    @Override
    @Transactional
    public CommaResponse remove(Long loginUserId, Long commaId) {
        // todo exception exception exception
        // 생ㅇ성, 수정, 삭제 순서로 login user id 로직을 추가하면서
        // login user id 와 comma 엔티티에 저장된 user id 를 비교하는 작업을
        // 동일하게 동작시키고 있는데 이걸 매번 service 에서 해주는 게 맞겠지?
        // repository 에서 db 를 통해 엔티티를 가져와야하는 작업이 필요하니까
        // 왠지 여러번의 반복이 이뤄지면서 다른 곳에서 처리해줄 수 있지도 않나 생각이 든다
        userRepository.findById(loginUserId)
            .orElseThrow(NoSuchElementException::new);

        Comma comma = commaRepository.findById(commaId)
            .orElseThrow(NoSuchElementException::new);

        validateUpdateComma(loginUserId, comma.getUserId());

        commaRepository.delete(comma);

        return new CommaResponse(comma.getId());
    }

    private void validateUpdateComma(Long loginUserId, Long writerId) {
        if (!writerId.equals(loginUserId)) {
            throw new UnAuthorizedUserException();
        }
    }
}
