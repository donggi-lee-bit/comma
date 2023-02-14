package commaproject.be.commaserver.domain.comment;

import commaproject.be.commaserver.domain.BaseEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE comment SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private Long userId;
    private String username;
    private Long commaId;
    private boolean deleted = Boolean.FALSE;

    public Comment update(String content) {
        this.content = content;
        return this;
    }

    public static Comment from(String content, Long userId, String username, Long commaId) {
        return new Comment(content, userId, username, commaId);
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }

    private Comment(String content, Long userId, String username, Long commaId) {
        this.content = content;
        this.userId = userId;
        this.username = username;
        this.commaId = commaId;
    }
}
