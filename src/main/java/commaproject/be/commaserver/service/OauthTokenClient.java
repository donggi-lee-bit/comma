package commaproject.be.commaserver.service;

import commaproject.be.commaserver.service.dto.OauthTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "token-client", url = "${kakao.access-token-uri}")
public interface OauthTokenClient {

    @PostMapping
    OauthTokenResponse getAccessToken(
        @RequestHeader("Content-Type") String contentType,
        @RequestParam(value = "grant_type") String grantType,
        @RequestParam(value = "client_id") String clientId,
        @RequestParam(value = "redirect_uri") String redirectUri,
        @RequestParam(value = "code") String code
        );
}
