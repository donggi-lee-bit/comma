package commaproject.be.commaserver.service.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(SnakeCaseStrategy.class)
public class OauthTokenResponse {

    private String tokenType;

    private String accessToken;

    private int expiresIn;

    private String refreshToken;

    private int refreshTokenExpiresIn;

}
