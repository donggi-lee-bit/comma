package commaproject.be.commaserver.service;

import commaproject.be.commaserver.service.dto.CommentDetailResponse;
import commaproject.be.commaserver.service.dto.CommentRequest;
import commaproject.be.commaserver.service.dto.CommentResponse;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    public CommentResponse create(Long commaId, CommentRequest commentRequest) {
        return null;
    }

    public CommentDetailResponse update(Long commaId, Long commentId, CommentRequest commentRequest) {
        return null;
    }
}
