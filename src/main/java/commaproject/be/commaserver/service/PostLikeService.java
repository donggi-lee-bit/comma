package commaproject.be.commaserver.service;

import commaproject.be.commaserver.service.dto.PostLikeRequest;
import commaproject.be.commaserver.service.dto.PostLikeResponse;


public interface PostLikeService {

    PostLikeResponse like(PostLikeRequest postLikeRequest, Long loginUserId, Long commaId);
}
