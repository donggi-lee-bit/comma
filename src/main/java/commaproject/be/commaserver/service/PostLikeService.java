package commaproject.be.commaserver.service;

import commaproject.be.commaserver.service.dto.PostLikeRequest;


public interface PostLikeService {

    void like(PostLikeRequest postLikeRequest, Long loginUserId, Long commaId);

    void unlike(PostLikeRequest postLikeRequest, Long loginUserId, Long commaId);

}
