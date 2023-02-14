package commaproject.be.commaserver.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

import com.fasterxml.jackson.databind.ObjectMapper;
import commaproject.be.commaserver.service.CommaSearchService;
import commaproject.be.commaserver.service.CommaService;
import commaproject.be.commaserver.service.CommentService;
import commaproject.be.commaserver.service.JwtProvider;
import commaproject.be.commaserver.service.PostLikeService;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommentDetailResponse;
import commaproject.be.commaserver.tool.TestWebConfig;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Import(TestWebConfig.class)
@ActiveProfiles("test")
public class InitControllerTest {

    @MockBean
    protected CommaService commaService;

    @MockBean
    protected CommaSearchService commaSearchService;

    @MockBean
    protected CommentService commentService;

    @MockBean
    protected PostLikeService postLikeService;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected JwtProvider jwtProvider;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .alwaysDo(document("{method-name}",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())))
            .build();
    }

    protected List<CommaDetailResponse> createCommaTestData() {
        List<CommentDetailResponse> comments1 = new ArrayList<>();
        comments1.add(new CommentDetailResponse(1L, 1L, "username1", "content1",
            LocalDateTime.of(2023, 2, 9, 13, 26),
            LocalDateTime.of(2023, 2, 9, 13, 27)));
        Long commaId1 = 1L;

        CommaDetailResponse commaDetailResponse1 = new CommaDetailResponse(
            commaId1, "title1", "content1", "username1", 1L, LocalDateTime.of(2022, 12, 27, 15, 13),2, comments1);


        List<CommentDetailResponse> comments2 = new ArrayList<>();
        comments2.add(new CommentDetailResponse(1L, 1L, "username2", "content1",
            LocalDateTime.of(2023, 2, 9, 13, 26),
            LocalDateTime.of(2023, 2, 9, 13, 27)));
        Long commaId2 = 2L;

        CommaDetailResponse commaDetailResponse2 = new CommaDetailResponse(
            commaId2, "title2", "content2", "username2", 2L, LocalDateTime.of(2022, 12, 28, 15, 13),3, comments2);

        List<CommaDetailResponse> commaDetailResponses = new ArrayList<>();
        commaDetailResponses.add(commaDetailResponse1);
        commaDetailResponses.add(commaDetailResponse2);
        return commaDetailResponses;
    }

    protected List<CommentDetailResponse> createCommentTestData() {
        List<CommentDetailResponse> comments = new ArrayList<>();

        comments.add(new CommentDetailResponse(1L, 1L, "username1", "content1",
            LocalDateTime.of(2023, 2, 9, 13, 26),
            LocalDateTime.of(2023, 2, 9, 13, 27)));

        comments.add(new CommentDetailResponse(2L, 1L, "username2", "content2",
            LocalDateTime.of(2023, 2, 9, 13, 26),
            LocalDateTime.of(2023, 2, 9, 13, 27)));

        comments.add(new CommentDetailResponse(3L, 1L, "username3", "content3",
            LocalDateTime.of(2023, 2, 9, 13, 26),
            LocalDateTime.of(2023, 2, 9, 13, 27)));

        return comments;
    }
}
