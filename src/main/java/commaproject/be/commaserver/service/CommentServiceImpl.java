package commaproject.be.commaserver.service;

import commaproject.be.commaserver.common.exception.comma.NotFoundCommaException;
import commaproject.be.commaserver.common.exception.user.NotFoundUserException;
import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.domain.comment.Comment;
import commaproject.be.commaserver.domain.user.User;
import commaproject.be.commaserver.repository.CommaRepository;
import commaproject.be.commaserver.repository.CommentRepository;
import commaproject.be.commaserver.repository.UserRepository;
import commaproject.be.commaserver.service.dto.CommentDetailResponse;
import commaproject.be.commaserver.service.dto.CommentRequest;
import commaproject.be.commaserver.service.dto.CommentResponse;
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
    public CommentDetailResponse update(Long loginUserId, Long commaId, Long commentId,
        CommentRequest commentRequest) {
        return null;
    }

    @Override
    public CommentResponse delete(Long loginUserId, Long commaId, Long commentId) {
        return null;
    }
}
