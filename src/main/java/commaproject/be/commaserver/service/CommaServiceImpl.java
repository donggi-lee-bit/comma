package commaproject.be.commaserver.service;

import commaproject.be.commaserver.common.exception.comma.NotFoundCommaException;
import commaproject.be.commaserver.common.exception.user.NotFoundUserException;
import commaproject.be.commaserver.common.exception.user.UnAuthorizedUserException;
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
        Comma comma = commaRepository.findById(commaId)
            .orElseThrow(NotFoundCommaException::new);

        List<CommentDetailResponse> comments = new ArrayList<>();

        return new CommaDetailResponse(
            comma.getId(),
            comma.getTitle(),
            comma.getContent(),
            comma.getUsername(),
            comma.getUserId(),
            comma.getCreatedAt(),
            comma.postLikeCount(),
            comments
            );
    }

    @Override
    public List<CommaDetailResponse> readAll() {
        List<Comma> commas = commaRepository.findAll();

        // todo likeCount, comments 상수 입력 상태

        return commas.stream()
            .map(comma -> new CommaDetailResponse(
                comma.getId(),
                comma.getTitle(),
                comma.getContent(),
                comma.getUsername(),
                comma.getUserId(),
                comma.getCreatedAt(),
                comma.postLikeCount(),
                new ArrayList<>()))
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public CommaResponse create(Long loginUserId, CommaRequest commaRequest) {
        User user = userRepository.findById(loginUserId)
            .orElseThrow(NotFoundUserException::new);

        Comma saveComma = commaRepository.save(
            Comma.from(commaRequest.getTitle(), commaRequest.getContent(),
                user.getUsername(), user.getId()));

        return new CommaResponse(saveComma.getId());
    }

    @Override
    @Transactional
    public CommaDetailResponse update(Long loginUserId, Long commaId, CommaRequest commaRequest) {
        Comma comma = commaRepository.findById(commaId)
            .orElseThrow(NotFoundCommaException::new);

        User user = userRepository.findById(loginUserId)
            .orElseThrow(NotFoundUserException::new);

        validateAuthorizedUserModifyComma(loginUserId, comma.getUserId());

        Comma updateComma = comma.update(
            commaRequest.getTitle(),
            commaRequest.getContent(),
            user.getUsername(),
            user.getId());

        return new CommaDetailResponse(
            updateComma.getId(),
            updateComma.getTitle(),
            updateComma.getContent(),
            updateComma.getUsername(),
            updateComma.getUserId(),
            updateComma.getCreatedAt(),
            updateComma.postLikeCount(),
            new ArrayList<>());
    }

    @Override
    @Transactional
    public CommaResponse remove(Long loginUserId, Long commaId) {
        userRepository.findById(loginUserId)
            .orElseThrow(NotFoundUserException::new);

        Comma comma = commaRepository.findById(commaId)
            .orElseThrow(NotFoundCommaException::new);

        validateAuthorizedUserModifyComma(loginUserId, comma.getUserId());

        commaRepository.delete(comma);

        return new CommaResponse(comma.getId());
    }

    private void validateAuthorizedUserModifyComma(Long loginUserId, Long writerId) {
        if (!writerId.equals(loginUserId)) {
            throw new UnAuthorizedUserException();
        }
    }
}
