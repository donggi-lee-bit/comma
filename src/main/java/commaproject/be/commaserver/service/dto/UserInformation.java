package commaproject.be.commaserver.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import commaproject.be.commaserver.common.CustomJsonDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonDeserialize(using = CustomJsonDeserializer.class)
public class UserInformation {

    private String username;
    private String email;
    private String userImageUri;

    public UserInformation(String username, String email, String userImageUri) {
        this.username = username;
        this.email = email;
        this.userImageUri = userImageUri;
    }
}
