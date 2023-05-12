package commaproject.be.commaserver.repository;

import commaproject.be.commaserver.domain.comma.Comma;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommaSearchRepository {

    Page<Comma> searchByCondition(Pageable pageable, LocalDate dateCondition, String usernameCondition);
}
