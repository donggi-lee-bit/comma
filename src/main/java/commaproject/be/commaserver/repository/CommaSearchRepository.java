package commaproject.be.commaserver.repository;

import commaproject.be.commaserver.domain.comma.Comma;
import java.time.LocalDateTime;
import java.util.List;

public interface CommaSearchRepository {

    List<Comma> searchByDateCondition(LocalDateTime start, LocalDateTime end);

    List<Comma> searchByUserCondition(String username);

    List<Comma> searchByUserDateCondition(String username, LocalDateTime start, LocalDateTime end);
}
