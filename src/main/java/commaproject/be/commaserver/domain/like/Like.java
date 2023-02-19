package commaproject.be.commaserver.domain.like;

import commaproject.be.commaserver.domain.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Like extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private boolean likeStatus = Boolean.FALSE;
    private Long userId;
    private Long commaId;

    private Like(boolean likeStatus, Long userId, Long commaId) {
        this.likeStatus = likeStatus;
        this.userId = userId;
        this.commaId = commaId;
    }

    public static Like of(Long loginUserId, Long commaId) {
        return new Like(Boolean.FALSE, loginUserId, commaId);
    }

    public void clickPostLike(boolean likeStatus) {
        this.likeStatus = likeStatus;
    }
}
