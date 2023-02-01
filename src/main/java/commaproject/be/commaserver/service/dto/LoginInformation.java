package commaproject.be.commaserver.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginInformation {

    private Long userId;
    private String username;
    private String userImageUri;
    private String email;
    private String accessToken;
    private String refreshToken;

    public LoginInformation(Long userId, String username, String userImageUri, String email,
        String accessToken, String refreshToken) {
        this.userId = userId;
        this.username = username;
        this.userImageUri = userImageUri;
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
