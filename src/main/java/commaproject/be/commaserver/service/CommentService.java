package commaproject.be.commaserver.service;

import commaproject.be.commaserver.domain.comment.Comment;
import commaproject.be.commaserver.service.dto.CommentDetailResponse;
import commaproject.be.commaserver.service.dto.CommentRequest;
import java.util.List;

public interface CommentService {

    Comment create(Long loginUserId, Long commaId, CommentRequest commentRequest);

    CommentDetailResponse update(Long loginUserId, Long commaId, Long commentId, CommentRequest commentRequest);

    Comment delete(Long loginUserId, Long commaId, Long commentId);

    List<CommentDetailResponse> readAll(Long commaId);

    CommentDetailResponse readOne(Long commaId, Long commentId);
}
