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
    private String username;
    private Long userId;

    public CommaRequest(String title, String content, String username, Long userId) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.userId = userId;
    }
}
