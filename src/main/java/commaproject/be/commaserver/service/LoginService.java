package commaproject.be.commaserver.service;

import commaproject.be.commaserver.domain.user.User;
import commaproject.be.commaserver.repository.UserRepository;
import commaproject.be.commaserver.service.dto.LoginInformation;
import commaproject.be.commaserver.service.dto.UserInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginService {

    private final OauthService oauthService;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public LoginInformation login(String code) {
        UserInformation userInformation = oauthService.oauth(code);

        User user = userRepository.findByEmail(userInformation.getEmail())
            .orElseGet(() -> userRepository.save(User.from(userInformation.getUsername(),
                userInformation.getEmail(), userInformation.getUserImageUri())));

        String accessToken = jwtProvider.generateAccessToken(user.getId());
        String refreshToken = jwtProvider.generateRefreshToken(user.getId());

        return new LoginInformation(user.getId(), user.getUsername(), user.getUserImageUri(),
            user.getEmail(), accessToken, refreshToken);
    }
}
