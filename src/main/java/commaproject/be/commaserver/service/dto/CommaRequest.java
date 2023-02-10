package commaproject.be.commaserver.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class CommaRequest {

    private String title;
    private String content;

    public CommaRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
