package commaproject.be.commaserver.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentDetailResponse {

    private Long id;
    private String username;
    private Long userId;
    private String content;
}
