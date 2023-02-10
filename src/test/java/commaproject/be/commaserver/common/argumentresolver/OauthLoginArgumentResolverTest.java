package commaproject.be.commaserver.common.argumentresolver;

import static org.assertj.core.api.Assertions.assertThat;

import commaproject.be.commaserver.service.InitIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;

class OauthLoginArgumentResolverTest extends InitIntegrationTest {

    @Autowired
    private OauthLoginArgumentResolver oauthLoginArgumentResolver;

    private MockHttpServletRequest mockHttpServletRequest;

    @BeforeEach
    void setUp() {
        mockHttpServletRequest = new MockHttpServletRequest();
    }

    @Test
    @DisplayName("올바른 유저의 token이 넘어오는 경우 로그인 id를 반환한다.")
    void oauth_argument_resolver_success() {
        mockHttpServletRequest.addHeader("Authorization", testData.testUserValidAuthorizationHeader);
        Object loginUserId = oauthLoginArgumentResolver.resolveArgument(null, null,
            new ServletWebRequest(mockHttpServletRequest), null);

        assertThat(loginUserId).isEqualTo(testData.testUser.getId());
    }
}
