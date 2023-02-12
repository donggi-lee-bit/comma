package commaproject.be.commaserver.service.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class CommentRequest {

    private String content;

    public CommentRequest(String content) {
        this.content = content;
    }
}
