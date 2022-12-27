package commaproject.be.commaserver.service.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommaDetailResponse {

    private Long id;
    private String title;
    private String content;
    private String username;
    private Long userId;
    private int likeCount;
    private List<CommentResponse> comments;

}
