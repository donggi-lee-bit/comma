package commaproject.be.commaserver.repository;

import commaproject.be.commaserver.domain.comma.Comma;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommaSearchRepository {

    Page<Comma> searchByDateCondition(LocalDate start, LocalDate end, Pageable pageable);

    Page<Comma> searchByUserCondition(String username, Pageable pageable);

    Page<Comma> searchByUserDateCondition(String username, LocalDate start, LocalDate end, Pageable pageable);
}
