package commaproject.be.commaserver.integration.auth;

import static commaproject.be.commaserver.integration.auth.stub.OAuthMocks.setupResponse;
import static org.assertj.core.api.Assertions.assertThat;

import com.github.tomakehurst.wiremock.client.WireMock;
import commaproject.be.commaserver.service.LoginService;
import commaproject.be.commaserver.service.OauthService;
import commaproject.be.commaserver.service.dto.LoginInformation;
import commaproject.be.commaserver.service.dto.UserInformation;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.TestPropertySource;

@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
    "kakao.access-token-uri=http://localhost:${wiremock.server.port}",
    "kakao.user-information-uri=http://localhost:${wiremock.server.port}"
})
@SpringBootTest
public class OauthLoginIntegrationTest {

    @Autowired
    private OauthService oauthService;

    @Autowired
    private LoginService loginService;

    @BeforeEach
    void setUp() throws IOException {
        setupResponse();
    }

    @Test
    @DisplayName("인가 코드로 auth 서버에서 유저 정보를 가져와 UserRepository에 유저를 저장하고 로그인에 성공한다")
    void oauth_login_success() {
        WireMock.setScenarioState("OAuth Login", "Started");

        UserInformation userInformation = oauthService.oauth("code");
        LoginInformation loginInformation = loginService.login("code");

        assertThat(userInformation).isNotNull();
        assertThat(loginInformation).isNotNull();
    }
}
