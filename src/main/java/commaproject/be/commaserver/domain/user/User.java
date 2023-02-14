package commaproject.be.commaserver.domain.user;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String userImageUri;

    @ElementCollection
    private Set<Long> likes = new HashSet<>();

    private User(String username, String email, String userImageUri) {
        this.username = username;
        this.email = email;
        this.userImageUri = userImageUri;
    }

    public static User from(String username, String email, String userImageUri) {
        return new User(username, email, userImageUri);
    }

    public void add(Long commaId) {
        this.likes.add(commaId);
    }

    /**
     * 좋아요 누른 게시글 id가 들어와서
     * likes 리스트에서 해당 id 유무를 반환
     * true 가 반환되면 예외 발생
     */

    public boolean isDuplicatePostLike(Long commaId) {
        return this.likes.contains(commaId);
    }

    public void unlike(Long commaId) {
        this.likes.remove(commaId);
    }
}
