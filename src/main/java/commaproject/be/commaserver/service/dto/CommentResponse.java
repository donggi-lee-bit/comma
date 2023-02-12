package commaproject.be.commaserver.service.dto;

import lombok.Getter;

@Getter
public class CommentResponse {

    private Long id;

    public CommentResponse(Long id) {
        this.id = id;
    }
}
