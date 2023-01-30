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

    private final KakaoProperties kakaoProperties;
    private final OauthTokenClient oauthTokenClient;
    private final OauthApiClient oauthApiClient;

    @Transactional
    public UserInformation oauth(String code) {
        OauthTokenResponse oauthTokenResponse = oauthTokenClient.getAccessToken(
            kakaoProperties.getGrantType(),
            kakaoProperties.getClientId(),
            kakaoProperties.getRedirectUri(),
            kakaoProperties.getResponseType()
        );

        String authorizationHeader = getAuthorizationHeader(oauthTokenResponse);

        return oauthApiClient.getUserInformation(authorizationHeader);
    }

    private String getAuthorizationHeader(OauthTokenResponse oauthTokenResponse) {
        return String.format("%s %s", oauthTokenResponse.getTokenType(),
            oauthTokenResponse.getAccessToken());
    }
}
