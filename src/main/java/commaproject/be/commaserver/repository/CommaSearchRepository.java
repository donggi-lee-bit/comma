package commaproject.be.commaserver.repository;

import commaproject.be.commaserver.domain.comma.Comma;
import java.time.LocalDate;
import java.util.List;

public interface CommaSearchRepository {

    List<Comma> searchByDateCondition(LocalDate start, LocalDate end);

    List<Comma> searchByUserCondition(String username);

    List<Comma> searchByUserDateCondition(String username, LocalDate start, LocalDate end);
}
