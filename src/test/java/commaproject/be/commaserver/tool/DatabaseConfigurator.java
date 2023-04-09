package commaproject.be.commaserver.tool;

import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.domain.comment.Comment;
import commaproject.be.commaserver.domain.user.User;
import commaproject.be.commaserver.repository.CommaRepository;
import commaproject.be.commaserver.repository.CommentRepository;
import commaproject.be.commaserver.repository.UserRepository;
import commaproject.be.commaserver.service.JwtProvider;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConfigurator implements InitializingBean {

    @Autowired
    private CommaRepository commaRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames = new ArrayList<>();

    public User testUser;
    public String testUserValidAuthorizationHeader;

    @Override
    public void afterPropertiesSet() {
        entityManager.unwrap(Session.class).doWork(this::extractTableNames);
    }

    public void clear() {
        entityManager.unwrap(Session.class).doWork(this::cleanUpDatabase);
    }

    public void initDataSource() {
        initUserData();
        initCommaData();
        initCommentData();
        initUserAuthorizationData();
    }

    private void initUserAuthorizationData() {
        testUserValidAuthorizationHeader =
            "Bearer " + jwtProvider.generateAccessToken(testUser.getId());
    }

    private void initUserData() {
        this.testUser = userRepository.save(
            User.from("donggi", "donggi@kakao.com", "donggi_image_uri.jpg")
        );
    }

    private void extractTableNames(Connection connection) throws SQLException {
        List<String> tableNames = new ArrayList<>();
        ResultSet tables = connection.getMetaData()
            .getTables(connection.getCatalog(), null, null, new String[]{"TABLE"});

        try (tables) {
            while (tables.next()) {
                tableNames.add(tables.getString("table_name"));
            }
            this.tableNames = tableNames;
        }
    }

    private void cleanUpDatabase(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = " + "0");
            for (String tableName : tableNames) {
                statement.executeUpdate("TRUNCATE TABLE " + tableName);
            }
            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = " + "1");
        }
    }

    /**
     * 3개의 게시글 데이터
     */
    private void initCommaData() {
        for (int i = 1; i <= 3; i++) {
            commaRepository.save(Comma.from("title1", "content1", testUser));
        }
    }

    /**
     * 댓글 아이디와 댓글 작성자 아이디는 같은 숫자
     * ex) commentId = 2L, userId = 2L
     */
    private void initCommentData() {
        Long commaId = 1L;
        for (int i = 1; i <= 3; i++) {
            commentRepository.save(Comment.from("content1", (long) i, "username1", commaId));
        }
    }
}
