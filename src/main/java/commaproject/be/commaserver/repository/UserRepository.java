package commaproject.be.commaserver.repository;

import commaproject.be.commaserver.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> FindByEmail(String email);
}
