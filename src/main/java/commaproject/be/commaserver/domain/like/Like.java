package commaproject.be.commaserver.domain.like;

import commaproject.be.commaserver.domain.BaseEntity;
import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.domain.user.User;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "likes")
public class Like extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comma_id")
    private Comma comma;

    private boolean likeStatus = Boolean.FALSE;

    private Like(User user, Comma comma, boolean likeStatus) {
        this.user = user;
        this.comma = comma;
        this.likeStatus = likeStatus;
    }

    public static Like of(User user, Comma comma) {
        return new Like(user, comma, Boolean.FALSE);
    }

    public void update(boolean likeStatus) {
        this.likeStatus = likeStatus;
    }
}
