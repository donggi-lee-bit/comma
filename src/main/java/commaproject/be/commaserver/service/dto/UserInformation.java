package commaproject.be.commaserver.service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserInformation {

    private final String username;
    private final String email;
    private final String userImageUri;

    // todo deserialize 해줘야함
}
