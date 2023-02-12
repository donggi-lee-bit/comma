package commaproject.be.commaserver.service;

import commaproject.be.commaserver.repository.CommaRepository;
import commaproject.be.commaserver.repository.UserRepository;
import commaproject.be.commaserver.tool.DatabaseConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public abstract class InitIntegrationTest {

    @Autowired
    protected CommaServiceImpl commaService;

    @Autowired
    protected CommentServiceImpl commentService;

    @Autowired
    protected LoginService loginService;

    @Autowired
    protected CommaRepository commaRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected DatabaseConfigurator testData;

    @BeforeEach
    void setUpDatabase() {
        testData.clear();
        testData.initDataSource();
    }
}
