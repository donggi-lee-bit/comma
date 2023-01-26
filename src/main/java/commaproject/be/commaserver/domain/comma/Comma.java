package commaproject.be.commaserver.domain.comma;

import commaproject.be.commaserver.domain.BaseEntity;
import commaproject.be.commaserver.service.dto.CommaRequest;
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

    public Comma update(CommaRequest commaRequest) {
        this.title = commaRequest.getTitle();
        this.content = commaRequest.getContent();
        this.username = commaRequest.getUsername();
        this.userId = commaRequest.getUserId();
        return this;
    }
}
