package commaproject.be.commaserver.service.dto;

import lombok.Getter;

@Getter
public class CommaResponse {

    private Long id;

    public CommaResponse(Long id) {
        this.id = id;
    }
}
