package commaproject.be.commaserver.service;

import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.domain.comment.Comment;
import commaproject.be.commaserver.domain.user.User;
import commaproject.be.commaserver.repository.CommaRepository;
import commaproject.be.commaserver.repository.CommentRepository;
import commaproject.be.commaserver.repository.PostLikeRepository;
import commaproject.be.commaserver.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    @Mock
    protected PostLikeRepository postLikeRepository;

    @Mock
    protected CommentRepository commentRepository;

    /**
     * 3개의 게시글 데이터
     */
    protected Page<Comma> setCommasData() {
        List<Comma> commas = new ArrayList<>();
        Long userId = 1L;
        Long commaId = 1L;
        for (int i = 1; i <= 3; i++) {
            Comma comma = Comma.from("title1", "content1", setUserData(userId));
            commas.add(comma);
            ReflectionTestUtils.setField(comma, "id", commaId);
        }

        Page<Comma> page = new PageImpl<>(commas);
        return page;
    }

    protected User setUserData(Long userId) {
        User user = User.from("username1", "email1", "kakao@kakao.com");
        ReflectionTestUtils.setField(user, "id", userId);
        return user;
    }

    protected Comma setCommaData(Long commaId, Long userId) {
        Comma comma = Comma.from("title1", "content1", setUserData(userId));
        ReflectionTestUtils.setField(comma, "id", commaId);
        return comma;
    }

    protected List<Comment> setCommentsData(Long commaId, Long userId) {
        Long commentId = 1L;
        List<Comment> comments = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Comment comment = Comment.from("content1", userId, "username1", commaId);
            comments.add(comment);
            ReflectionTestUtils.setField(comment, "id", commentId);
        }
        return comments;
    }
}
