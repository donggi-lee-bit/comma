package commaproject.be.commaserver.controller;

import commaproject.be.commaserver.common.BaseResponse;
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

    // todo BaseResponse 정적 메서드 사용이 더 좋을듯
        return new BaseResponse("code", "OK", loginInformation);
    }
}
