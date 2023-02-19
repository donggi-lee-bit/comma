package commaproject.be.commaserver.service;

public interface PostLikeService {

    void like(Long loginUserId, Long commaId);

    void unlike(Long loginUserId, Long commaId);

}
