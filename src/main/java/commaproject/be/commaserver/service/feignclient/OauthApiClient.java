package commaproject.be.commaserver.service.feignclient;

import commaproject.be.commaserver.service.dto.UserInformation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "api-client", url = "${kakao.user-information-uri}")
public interface OauthApiClient {

    @GetMapping("/v2/user/me")
    UserInformation getUserInformation(@RequestHeader("Authorization") String accessToken);
}
