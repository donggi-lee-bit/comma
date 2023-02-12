package commaproject.be.commaserver.service;

import commaproject.be.commaserver.common.exception.comma.NotFoundCommaException;
import commaproject.be.commaserver.common.exception.comment.NotFoundCommentException;
import commaproject.be.commaserver.common.exception.user.NotFoundUserException;
import commaproject.be.commaserver.common.exception.user.UnAuthorizedUserException;
import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.domain.comment.Comment;
import commaproject.be.commaserver.domain.user.User;
import commaproject.be.commaserver.repository.CommaRepository;
import commaproject.be.commaserver.repository.CommentRepository;
import commaproject.be.commaserver.repository.UserRepository;
import commaproject.be.commaserver.service.dto.CommentDetailResponse;
import commaproject.be.commaserver.service.dto.CommentRequest;
import commaproject.be.commaserver.service.dto.CommentResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final CommaRepository commaRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CommentResponse create(Long loginUserId, Long commaId, CommentRequest commentRequest) {
        User user = userRepository.findById(loginUserId)
            .orElseThrow(NotFoundUserException::new);

        Comma comma = commaRepository.findById(commaId)
            .orElseThrow(NotFoundCommaException::new);

        Comment comment = commentRepository.save(
            Comment.from(
                commentRequest.getContent(), user.getId(),
                user.getUsername(),
                comma.getId()
            ));
        return new CommentResponse(comment.getId());
    }

    @Override
    @Transactional
    public CommentDetailResponse update(Long loginUserId, Long commaId, Long commentId,
        CommentRequest commentRequest) {
        User user = userRepository.findById(loginUserId)
            .orElseThrow(NotFoundUserException::new);

        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(NotFoundCommentException::new);

        validateUpdateComment(loginUserId, comment.getUserId());

        Comment updateComment = comment.update(commentRequest.getContent());

        return new CommentDetailResponse(
            updateComment.getId(),
            updateComment.getUserId(),
            updateComment.getContent(),
            updateComment.getCreatedAt(),
            updateComment.getUpdatedAt()
            );
    }

    @Override
    @Transactional
    public Comment delete(Long loginUserId, Long commaId, Long commentId) {
        User user = userRepository.findById(loginUserId)
            .orElseThrow(NotFoundUserException::new);

        Comma comma = commaRepository.findById(commaId)
            .orElseThrow(NotFoundCommaException::new);

        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(NotFoundCommentException::new);

        validateUpdateComment(user.getId(), comment.getUserId());

        comment.delete();

        return comment;
    }

    @Override
    public List<CommentDetailResponse> readAll(Long commaId) {
        Comma comma = commaRepository.findById(commaId)
            .orElseThrow(NotFoundCommaException::new);

        List<Comment> comments = commentRepository.findAllByCommaId(comma.getId());

        return comments.stream()
            .map(comment -> new CommentDetailResponse(
                comment.getId(),
                comment.getUserId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()))
            .collect(Collectors.toUnmodifiableList());
    }

    private void validateUpdateComment(Long loginUserId, Long commenterId) {
        if (!commenterId.equals(loginUserId)) {
            throw new UnAuthorizedUserException();
        }
    }
}
