package commaproject.be.commaserver.integration.auth;

import static commaproject.be.commaserver.integration.auth.stub.OAuthMocks.setupResponse;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import commaproject.be.commaserver.integration.InitIntegrationTest;
import commaproject.be.commaserver.service.LoginService;
import commaproject.be.commaserver.service.dto.LoginInformation;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles("test")
@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
    "kakao.access-token-uri=http://localhost:${wiremock.server.port}",
    "kakao.user-information-uri=http://localhost:${wiremock.server.port}"
})
@SpringBootTest
public class OauthLoginIntegrationTest extends InitIntegrationTest {

    @Autowired
    private LoginService loginService;

    @BeforeEach
    void setUp() throws IOException {
        setupResponse();
    }

    @Test
    @DisplayName("인가 코드로 auth 서버에서 유저 정보를 가져와 UserRepository에 유저를 저장하고 로그인에 성공한다")
    void oauth_login_success() {
        LoginInformation loginInformation = loginService.login("code");

        assertSoftly(softly -> {
            softly.assertThat(loginInformation.getUserId()).isEqualTo(2);
            softly.assertThat(loginInformation.getUsername()).isEqualTo("donggi");
            softly.assertThat(loginInformation.getUserImageUri()).isEqualTo("http://yyy.kakaoo.com/img_110x110.jpg");
            softly.assertThat(loginInformation.getEmail()).isEqualTo("donggi@gmail.com");
        });
    }
}
