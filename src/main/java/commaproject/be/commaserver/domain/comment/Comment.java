package commaproject.be.commaserver.domain.comment;

import commaproject.be.commaserver.domain.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment", indexes = {@Index(name = "idx_comma_id", columnList = "commaId")})
@SQLDelete(sql = "UPDATE comment SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private Long userId;
    private String username;
    private Long commaId;

    @Column(columnDefinition = "BOOLEAN")
    private boolean deleted = Boolean.FALSE;

    private Comment(String content, Long userId, String username, Long commaId) {
        this.content = content;
        this.userId = userId;
        this.username = username;
        this.commaId = commaId;
    }

    public static Comment from(String content, Long userId, String username, Long commaId) {
        return new Comment(content, userId, username, commaId);
    }

    public Comment update(String content) {
        this.content = content;
        return this;
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
