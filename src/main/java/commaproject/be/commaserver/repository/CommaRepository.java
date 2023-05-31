package commaproject.be.commaserver.repository;

import commaproject.be.commaserver.domain.comma.Comma;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommaRepository extends JpaRepository<Comma, Long>, CommaSearchRepository {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Comma c where c.id = :id and c.deleted = false")
    Optional<Comma> findByIdWithPessimisticLock(@Param("id") Long id);
}
