package commaproject.be.commaserver.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponse {

    private Long commentId;
    private String username;
    private Long userId;
    private String content;
}
