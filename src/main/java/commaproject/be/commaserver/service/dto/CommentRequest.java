package commaproject.be.commaserver.service.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CommentRequest {

    private Long commaId;
    private String content;
    private String username;
    private Long userId;
}
