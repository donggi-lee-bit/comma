package commaproject.be.commaserver.domain.comma;

import commaproject.be.commaserver.common.exception.user.UnAuthorizedUserException;
import commaproject.be.commaserver.domain.BaseEntity;
import commaproject.be.commaserver.domain.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "comma", indexes = {
    @Index(name = "idx_created_at", columnList = "createdAt")
})
@SQLDelete(sql = "UPDATE comma SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Comma extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String username;

    @Column(columnDefinition = "BOOLEAN")
    private boolean deleted = Boolean.FALSE;

    private Comma(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.username = user.getUsername();
    }

    public static Comma from(String title, String content, User user) {
        return new Comma(title, content, user);
    }

    public Comma update(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.username = user.getUsername();
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
