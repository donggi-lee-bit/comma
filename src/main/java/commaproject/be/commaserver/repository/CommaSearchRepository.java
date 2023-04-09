package commaproject.be.commaserver.repository;

import commaproject.be.commaserver.domain.comma.Comma;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CommaSearchRepository {

    List<Comma> searchByDateCondition(LocalDate start, LocalDate end, Pageable pageable);

    List<Comma> searchByUserCondition(String username, Pageable pageable);

    List<Comma> searchByUserDateCondition(String username, LocalDate start, LocalDate end, Pageable pageable);
}
