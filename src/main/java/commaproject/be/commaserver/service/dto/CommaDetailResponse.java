package commaproject.be.commaserver.service.dto;

import commaproject.be.commaserver.domain.comment.Comment;
import commaproject.be.commaserver.domain.user.User;
import commaproject.be.commaserver.service.dto.CommentResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommaDetailResponse {

    private Long commaId;
    private String title;
    private String content;
    private String username;
    private Long userId;
    private int likeCount;
    private List<CommentResponse> comments;

}
