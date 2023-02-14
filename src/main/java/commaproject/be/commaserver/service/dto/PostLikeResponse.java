package commaproject.be.commaserver.service.dto;

import lombok.Getter;

@Getter
public class PostLikeResponse {

    private Long commaId;
    private boolean likeStatus;

    public PostLikeResponse(Long commaId, boolean likeStatus) {
        this.commaId = commaId;
        this.likeStatus = likeStatus;
    }
}
