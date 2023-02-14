package commaproject.be.commaserver.service;

import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.domain.user.User;
import commaproject.be.commaserver.repository.CommaRepository;
import commaproject.be.commaserver.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class InitServiceTest {

    @InjectMocks
    protected CommaServiceImpl commaService;

    @InjectMocks
    protected PostLikeServiceImpl postLikeService;

    @Mock
    protected CommaRepository commaRepository;

    @Mock
    protected UserRepository userRepository;

    protected List<Comma> setCommasData() {
        List<Comma> commas = new ArrayList<>();
        Long userId = 1L;
        for (int i = 1; i <= 3; i++) {
            commas.add(Comma.from("title1", "content1", "username1", userId));
        }
        return commas;
    }

    protected User setUserData(Long userId) {
        User user = User.from("username1", "email1", "kakao@kakao.com");
        ReflectionTestUtils.setField(user, "id", userId);
        return user;
    }

    protected Comma setCommaData(Long commaId, Long userId) {
        Comma comma = Comma.from("title1", "content1", "username1", userId);
        ReflectionTestUtils.setField(comma, "id", commaId);
        return comma;
    }

}
