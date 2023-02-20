package commaproject.be.commaserver.domain.comma;

import commaproject.be.commaserver.common.exception.user.UnAuthorizedUserException;
import commaproject.be.commaserver.domain.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE comma SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Comma extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String username;
    private Long userId;
    private boolean deleted = Boolean.FALSE;

    private Comma(String title, String content, String username, Long userId) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.userId = userId;
    }

    public static Comma from(String title, String content, String username, Long userId) {
        return new Comma(title, content, username, userId);
    }

    public Comma update(String title, String content, String username, Long userId) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.userId = userId;
        return this;
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void validateAuthorizedUserModifyComma(Long loginUserId, Long writerId) {
        if (!writerId.equals(loginUserId)) {
            throw new UnAuthorizedUserException();
        }
    }
}
