package commaproject.be.commaserver.repository;

import commaproject.be.commaserver.domain.comma.Comma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommaRepository extends JpaRepository<Comma, Long>, CommaSearchRepository {

}
