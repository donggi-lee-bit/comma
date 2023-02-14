package commaproject.be.commaserver.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class PostLikeRequest {

    private boolean postLikeStatus;

    public PostLikeRequest(boolean postLikeStatus) {
        this.postLikeStatus = postLikeStatus;
    }
}
