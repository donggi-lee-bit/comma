package commaproject.be.commaserver.service.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class CommaDetailResponse {

    private Long id;
    private String title;
    private String content;
    private int view;
    private String username;
    private Long userId;
    private LocalDateTime createdAt;
    private Long postLikeCount;
    private List<CommentDetailResponse> comments;

    public CommaDetailResponse(Long id, String title, String content, int view, String username,
        Long userId, LocalDateTime createdAt, Long postLikeCount,
        List<CommentDetailResponse> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.view = view;
        this.username = username;
        this.userId = userId;
        this.createdAt = createdAt;
        this.postLikeCount = postLikeCount;
        this.comments = comments;
    }
}
