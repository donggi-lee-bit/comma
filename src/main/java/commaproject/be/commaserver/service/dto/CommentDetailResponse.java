package commaproject.be.commaserver.service.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentDetailResponse {

    private Long id;
    private Long userId;
    private String username;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public CommentDetailResponse(Long id, Long userId, String username, String content,
        LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.content = content;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }
}
