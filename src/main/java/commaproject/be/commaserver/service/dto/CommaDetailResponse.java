package commaproject.be.commaserver.service.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class CommaDetailResponse {

    private Long id;
    private String title;
    private String content;
    private String username;
    private Long userId;
    private LocalDateTime createdAt;
    private int postLikeCount;
    private List<CommentDetailResponse> comments;

    public CommaDetailResponse(Long id, String title, String content, String username, Long userId,
        LocalDateTime createdAt, int postLikeCount, List<CommentDetailResponse> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.username = username;
        this.userId = userId;
        this.createdAt = createdAt;
        this.postLikeCount = postLikeCount;
        this.comments = comments;
    }
}
