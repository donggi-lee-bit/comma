package commaproject.be.commaserver.service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginInformation {

    private final Long userId;
    private final String username;
    private final String userImageUri;
    private final String email;
    private final String accessToken;
    private final String refreshToken;

}
