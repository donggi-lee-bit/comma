package commaproject.be.commaserver.domain.user;

import java.util.ArrayList;
import java.util.List;
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
    private List<Long> likes = new ArrayList<>();

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
}
