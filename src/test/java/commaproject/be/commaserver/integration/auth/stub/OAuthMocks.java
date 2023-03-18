package commaproject.be.commaserver.integration.auth.stub;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.util.StreamUtils.copyToString;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.http.MediaType;

public class OAuthMocks {

    public static void setupResponse() throws IOException {
        setupMockTokenResponse();
        setupMockUserInformationResponse();
    }

    public static void setupMockTokenResponse() throws IOException {
        stubFor(post(urlEqualTo("/?client_id=clientId&redirect_uri=redirectUri&code=code"))
            .willReturn(aResponse()
                .withStatus(OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(getMockResponseBodyByPath("payload/oauth-token-response.json"))
            )
        );
    }

    public static void setupMockUserInformationResponse() throws IOException {
        stubFor(get(urlEqualTo("/v2/user/me"))
            .withHeader("Authorization", equalTo("bearer accessToken"))
            .willReturn(aResponse()
                .withStatus(OK.value())
                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .withBody(getMockResponseBodyByPath("payload/oauth-login-response.json"))
            )
        );
    }

    private static String getMockResponseBodyByPath(String path) throws IOException {
        return copyToString(getMockResourceStream(path), defaultCharset());
    }

    private static InputStream getMockResourceStream(String path) {
        return OAuthMocks.class.getClassLoader().getResourceAsStream(path);
    }

}
