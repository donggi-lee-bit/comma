package commaproject.be.commaserver.controller;

import static commaproject.be.commaserver.common.response.ResponseCodeAndMessage.OAUTH_LOGIN_SUCCESS;

import commaproject.be.commaserver.common.response.BaseResponse;
import commaproject.be.commaserver.service.LoginService;
import commaproject.be.commaserver.service.dto.LoginInformation;
import commaproject.be.commaserver.service.dto.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/login")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public BaseResponse<LoginInformation> login(@RequestBody LoginRequest loginRequest) {
        LoginInformation loginInformation = loginService.login(loginRequest.getCode());

        return new BaseResponse<>(OAUTH_LOGIN_SUCCESS, loginInformation);
    }
}
