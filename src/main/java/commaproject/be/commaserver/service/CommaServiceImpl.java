package commaproject.be.commaserver.service;

import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.repository.CommaRepository;
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
    public CommaResponse create(CommaRequest commaRequest) {
        Comma saveComma = commaRepository.save(
            Comma.from(commaRequest.getTitle(), commaRequest.getContent(),
                commaRequest.getUsername(), commaRequest.getUserId()));

        return new CommaResponse(saveComma.getId());
    }

    @Override
    @Transactional
    public CommaDetailResponse update(Long commaId, CommaRequest commaRequest) {
        // todo likeCount, comments 상수 입력 상태
        Comma findComma = commaRepository.findById(commaId)
            .orElseThrow(NoSuchElementException::new);

        Comma updateComma = findComma.update(commaRequest);

        return new CommaDetailResponse(updateComma.getId(), updateComma.getTitle(),
            updateComma.getContent(), updateComma.getUsername(), updateComma.getUserId(),
            updateComma.getCreatedAt(), 0, new ArrayList<>());
    }

    @Override
    @Transactional
    public CommaResponse remove(Long commaId) {
        Comma findComma = commaRepository.findById(commaId)
            .orElseThrow(NoSuchElementException::new);

        commaRepository.delete(findComma);

        return new CommaResponse(findComma.getId());
    }
}
