package commaproject.be.commaserver.service.dto;

import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@EqualsAndHashCode
public class CommaSearchConditionRequest {

    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
    private final LocalDateTime date;
    private final String username;

    public CommaSearchConditionRequest(LocalDateTime date, String username) {
        this.date = date;
        this.username = username;
    }
}
