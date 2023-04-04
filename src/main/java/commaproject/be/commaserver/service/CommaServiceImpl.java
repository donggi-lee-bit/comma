package commaproject.be.commaserver.service;

import commaproject.be.commaserver.common.exception.PageSizeOutOfBoundsException;
import commaproject.be.commaserver.common.exception.comma.NotFoundCommaException;
import commaproject.be.commaserver.common.exception.user.NotFoundUserException;
import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.domain.comment.Comment;
import commaproject.be.commaserver.domain.user.User;
import commaproject.be.commaserver.repository.CommaRepository;
import commaproject.be.commaserver.repository.CommentRepository;
import commaproject.be.commaserver.repository.PostLikeRepository;
import commaproject.be.commaserver.repository.UserRepository;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommaRequest;
import commaproject.be.commaserver.service.dto.CommaResponse;
import commaproject.be.commaserver.service.dto.CommentDetailResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommaServiceImpl implements CommaService {

    private final CommaRepository commaRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    @Override
    public CommaDetailResponse readOne(Long commaId) {
        Comma comma = commaRepository.findById(commaId)
            .orElseThrow(NotFoundCommaException::new);

        return new CommaDetailResponse(
            comma.getId(),
            comma.getTitle(),
            comma.getContent(),
            comma.getUsername(),
            comma.getUser().getId(),
            comma.getCreatedAt(),
            getPostLikeCount(commaId),
            getCommentDetailResponses(comma.getId())
        );
    }

    @Override
    public List<CommaDetailResponse> readAll(Pageable pageable) {
        validatePageSize(pageable);
        Page<Comma> commas = commaRepository.findAll(pageable);

        return commas.stream()
            .map(comma -> new CommaDetailResponse(
                comma.getId(),
                comma.getTitle(),
                comma.getContent(),
                comma.getUsername(),
                comma.getUser().getId(),
                comma.getCreatedAt(),
                getPostLikeCount(comma.getId()),
                getCommentDetailResponses(comma.getId())))
            .collect(Collectors.toUnmodifiableList());
    }

    @Override
    @Transactional
    public CommaResponse create(Long loginUserId, CommaRequest commaRequest) {
        User user = userRepository.findById(loginUserId)
            .orElseThrow(NotFoundUserException::new);

        Comma saveComma = commaRepository.save(
            Comma.from(commaRequest.getTitle(), commaRequest.getContent(),
                user));

        return new CommaResponse(saveComma.getId());
    }

    @Override
    @Transactional
    public CommaDetailResponse update(Long loginUserId, Long commaId, CommaRequest commaRequest) {
        Comma comma = commaRepository.findById(commaId)
            .orElseThrow(NotFoundCommaException::new);

        User user = userRepository.findById(loginUserId)
            .orElseThrow(NotFoundUserException::new);

        comma.validateAuthorizedUserModifyComma(loginUserId, comma.getUser().getId());

        Comma updateComma = comma.update(
            commaRequest.getTitle(),
            commaRequest.getContent(),
            user
        );

        return new CommaDetailResponse(
            updateComma.getId(),
            updateComma.getTitle(),
            updateComma.getContent(),
            updateComma.getUsername(),
            updateComma.getUser().getId(),
            updateComma.getCreatedAt(),
            getPostLikeCount(updateComma.getId()),
            getCommentDetailResponses(comma.getId()));
    }

    @Override
    @Transactional
    public Comma remove(Long loginUserId, Long commaId) {
        userRepository.findById(loginUserId)
            .orElseThrow(NotFoundUserException::new);

        Comma comma = commaRepository.findById(commaId)
            .orElseThrow(NotFoundCommaException::new);

        comma.validateAuthorizedUserModifyComma(loginUserId, comma.getUser().getId());

        comma.delete();

        return comma;
    }

    private List<CommentDetailResponse> getCommentDetailResponses(Long commaId) {
        List<Comment> comments = commentRepository.findAllByCommaId(commaId);

        return comments.stream()
            .map(comment -> new CommentDetailResponse(
                comment.getId(),
                comment.getUserId(),
                comment.getUsername(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()))
            .collect(Collectors.toUnmodifiableList());
    }

    private Long getPostLikeCount(Long commaId) {
        return postLikeRepository.countLikeByCommaIdAndLikeStatus(commaId, true);
    }

    private void validatePageSize(Pageable pageable) {
        if (pageable.getPageSize() > 100) {
            throw new PageSizeOutOfBoundsException();
        }
    }
}
