package commaproject.be.commaserver.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import commaproject.be.commaserver.service.dto.UserInformation;
import java.io.IOException;

public class CustomJsonDeserializer extends JsonDeserializer<UserInformation> {

    @Override
    public UserInformation deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        JsonNode kakaoAccountNode = node.get("kakao_account");

        String username = kakaoAccountNode.get("profile")
            .get("nickname")
            .asText();
        String userImageUri = kakaoAccountNode.get("profile")
            .get("profile_image_url")
            .asText();
        String email = kakaoAccountNode.get("email")
            .asText();

        return new UserInformation(username, email, userImageUri);
    }
}
