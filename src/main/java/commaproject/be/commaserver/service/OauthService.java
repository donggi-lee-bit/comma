package commaproject.be.commaserver.service;

import commaproject.be.commaserver.service.dto.properties.KakaoProperties;
import commaproject.be.commaserver.service.dto.OauthTokenResponse;
import commaproject.be.commaserver.service.dto.UserInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OauthService {

    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private final KakaoProperties kakaoProperties;
    private final OauthTokenClient oauthTokenClient;
    private final OauthApiClient oauthApiClient;

    @Transactional
    public UserInformation oauth(String code) {
        OauthTokenResponse oauthTokenResponse = oauthTokenClient.getAccessToken(
            CONTENT_TYPE,
            kakaoProperties.getGrantType(),
            kakaoProperties.getClientId(),
            kakaoProperties.getRedirectUri(),
            code
        );

        String authorizationHeader = getAuthorizationHeader(oauthTokenResponse);
        return oauthApiClient.getUserInformation(authorizationHeader);
    }

    private String getAuthorizationHeader(OauthTokenResponse oauthTokenResponse) {
        return String.format("%s %s", oauthTokenResponse.getTokenType(),
            oauthTokenResponse.getAccessToken());
    }
}
