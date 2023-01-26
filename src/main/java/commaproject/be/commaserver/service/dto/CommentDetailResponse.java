package commaproject.be.commaserver.service.dto;

import lombok.Getter;

@Getter
public class CommentDetailResponse {

    private Long id;
    private String username;
    private Long userId;
    private String content;

    public CommentDetailResponse(Long id, String username, Long userId, String content) {
        this.id = id;
        this.username = username;
        this.userId = userId;
        this.content = content;
    }
}
