package commaproject.be.commaserver.service.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class SearchConditionRequest {

    private String date;
    private String username;
}
