package commaproject.be.commaserver.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class CommaSearchConditionRequest {

    private String date;
    private String username;

    public CommaSearchConditionRequest(String date, String username) {
        this.date = date;
        this.username = username;
    }
}
