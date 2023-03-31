package commaproject.be.commaserver.service.dto;

import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@EqualsAndHashCode
public class CommaSearchConditionRequest {

    @DateTimeFormat(pattern = "yyyyMMdd")
    private final LocalDate date;
    private final String username;

    public CommaSearchConditionRequest(LocalDate date, String username) {
        this.date = date;
        this.username = username;
    }
}
