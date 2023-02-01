package commaproject.be.commaserver.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginRequest {

    private String code;

    public LoginRequest(String code) {
        this.code = code;
    }
}
