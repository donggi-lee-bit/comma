package commaproject.be.commaserver.service;

import commaproject.be.commaserver.service.dto.CommentDetailResponse;
import commaproject.be.commaserver.service.dto.CommentRequest;
import commaproject.be.commaserver.service.dto.CommentResponse;

public interface CommentService {

    CommentResponse create(Long loginUserId, Long commaId, CommentRequest commentRequest);

    CommentDetailResponse update(Long loginUserId, Long commaId, Long commentId, CommentRequest commentRequest);

    CommentResponse delete(Long loginUserId, Long commaId, Long commentId);
}
