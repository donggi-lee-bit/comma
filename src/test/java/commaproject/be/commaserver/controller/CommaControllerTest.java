package commaproject.be.commaserver.controller;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import commaproject.be.commaserver.common.BaseResponse;
import commaproject.be.commaserver.service.CommaService;
import commaproject.be.commaserver.service.JwtProvider;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommaRequest;
import commaproject.be.commaserver.service.dto.CommaResponse;
import commaproject.be.commaserver.service.dto.CommentDetailResponse;
import commaproject.be.commaserver.tool.TestWebConfig;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class})
@WebMvcTest(CommaController.class)
@Import(TestWebConfig.class)
@ActiveProfiles("test")
class CommaControllerTest {

    @MockBean
    private CommaService commaService;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtProvider jwtProvider;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .alwaysDo(document("{method-name}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
            .build();
    }

    @Test
    @DisplayName("특정 회고 조회 성공")
    void read_comma_success() throws Exception {

        // given
        List<CommentDetailResponse> comments = new ArrayList<>();
        comments.add(new CommentDetailResponse(1L, "username1", 1L, "content1"));
        Long commaId = 1L;
        CommaDetailResponse commaDetailResponse = new CommaDetailResponse(
            commaId, "title1", "content1", "username1", 1L, LocalDateTime.of(2022, 12, 27, 15, 13),1, comments);
        BaseResponse<CommaDetailResponse> baseResponse = new BaseResponse<>("200", "OK",
            commaDetailResponse);

        when(commaService.readOne(commaId)).thenReturn(commaDetailResponse);

        // when
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/commas/{commaId}", commaId)
                .content(objectMapper
                    .registerModule(new JavaTimeModule())
                    .writeValueAsString(baseResponse))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


        // then
        result
            .andExpect(status().isOk())
            .andDo(
                document(
                    "comma-get",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        parameterWithName("commaId").description("회고 아이디")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("회고 아이디"),
                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("회고 제목"),
                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("회고 내용"),
                        fieldWithPath("data.username").type(JsonFieldType.STRING).description("회고 작성자"),
                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("회고 작성자 아이디"),
                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("회고 작성된 시간"),
                        fieldWithPath("data.likeCount").type(JsonFieldType.NUMBER).description("좋아요 개수"),
                        fieldWithPath("data.comments[].id").type(JsonFieldType.NUMBER).description("댓글 아이디"),
                        fieldWithPath("data.comments[].username").type(JsonFieldType.STRING).description("댓글 작성자"),
                        fieldWithPath("data.comments[].userId").type(JsonFieldType.NUMBER).description("댓글 작성자 아이디"),
                        fieldWithPath("data.comments[].content").type(JsonFieldType.STRING).description("댓글 내용")
                    )
            ));
    }

    @Test
    @DisplayName("전체 회고 조회 성공")
    void read_all_comma_success() throws Exception {

        // given
        List<CommaDetailResponse> commaDetailResponses = createTestData();

        BaseResponse<List<CommaDetailResponse>> baseResponse = new BaseResponse<>("200", "OK",
            commaDetailResponses);

        when(commaService.readAll()).thenReturn(commaDetailResponses);

        // when
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/commas")
                .content(objectMapper
                    .registerModule(new JavaTimeModule())
                    .writeValueAsString(baseResponse))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result
            .andExpect(status().isOk())
            .andDo(
                document(
                    "comma-all-get",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                        fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("회고 아이디"),
                        fieldWithPath("data[].title").type(JsonFieldType.STRING).description("회고 제목"),
                        fieldWithPath("data[].content").type(JsonFieldType.STRING).description("회고 내용"),
                        fieldWithPath("data[].username").type(JsonFieldType.STRING).description("회고 작성자"),
                        fieldWithPath("data[].userId").type(JsonFieldType.NUMBER).description("회고 작성자 아이디"),
                        fieldWithPath("data[].likeCount").type(JsonFieldType.NUMBER).description("좋아요 개수"),
                        fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("회고 작성된 시간"),
                        fieldWithPath("data[].comments[].id").type(JsonFieldType.NUMBER).description("댓글 아이디"),
                        fieldWithPath("data[].comments[].username").type(JsonFieldType.STRING).description("댓글 작성자"),
                        fieldWithPath("data[].comments[].userId").type(JsonFieldType.NUMBER).description("댓글 작성자 아이디"),
                        fieldWithPath("data[].comments[].content").type(JsonFieldType.STRING).description("댓글 내용")
                    )
                ));
    }

    @Test
    @DisplayName("회고 생성 성공")
    void create_comma_success() throws Exception {

        // given
        Long userId = 1L;
        Long commaId = 1L;
        CommaRequest commaRequest = new CommaRequest("title1", "content1");
        CommaResponse commaResponse = new CommaResponse(commaId);

        when(commaService.create(userId, commaRequest)).thenReturn(commaResponse);

        // when
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.post("/api/commas")
                .header("Authorization",
                    "Bearer " + jwtProvider.generateAccessToken(userId))
                .content(objectMapper
                    .writeValueAsString(commaRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));


        // then
        result
            .andExpect(status().isOk())
            .andDo(
                document(
                    "comma-post",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("생성된 회고 아이디")
                    )
                ));
    }

    @Test
    @DisplayName("회고 수정 성공")
    void update_comma_success() throws Exception {

        // given
        Long commaId = 1L;
        Long userId = 1L;
        CommaRequest commaRequest = new CommaRequest("title1", "content1");

        List<CommentDetailResponse> comments = new ArrayList<>();
        comments.add(new CommentDetailResponse(1L, "username1", 1L, "content1"));

        CommaDetailResponse commaDetailResponse = new CommaDetailResponse(commaId, "title1", "content1", "username1", userId, LocalDateTime.of(2022, 12, 28, 15, 30), 1, comments);

        when(commaService.update(userId, commaId, commaRequest)).thenReturn(commaDetailResponse);

        // when
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.put("/api/commas/{commaId}", commaId)
                .header("Authorization",
                    "Bearer " + jwtProvider.generateAccessToken(userId))
                .content(objectMapper
                    .writeValueAsString(commaRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result
            .andExpect(status().isOk())
            .andDo(
                document(
                    "comma-update",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        parameterWithName("commaId").description("수정된 회고 아이디")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("회고 아이디"),
                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("수정된 회고 제목"),
                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("수정된 회고 내용"),
                        fieldWithPath("data.username").type(JsonFieldType.STRING).description("수정한 회고 작성자"),
                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("수정한 회고 작성자 아이디"),
                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("회고 작성된 시간"),
                        fieldWithPath("data.likeCount").type(JsonFieldType.NUMBER).description("좋아요 개수"),
                        fieldWithPath("data.comments[].id").type(JsonFieldType.NUMBER).description("댓글 아이디"),
                        fieldWithPath("data.comments[].username").type(JsonFieldType.STRING).description("댓글 작성자"),
                        fieldWithPath("data.comments[].userId").type(JsonFieldType.NUMBER).description("댓글 작성자 아이디"),
                        fieldWithPath("data.comments[].content").type(JsonFieldType.STRING).description("댓글 내용")
                    )
                ));
    }

    @Test
    @DisplayName("회고 삭제 성공")
    void delete_comma_success() throws Exception {

        // given
        Long commaId = 1L;
        Long loginUserId = 1L;
        CommaResponse commaResponse = new CommaResponse(commaId);

        when(commaService.remove(loginUserId, commaId)).thenReturn(commaResponse);

        // when
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.delete("/api/commas/{commaId}", commaId)
                .header("Authorization",
                    "Bearer " + jwtProvider.generateAccessToken(loginUserId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result
            .andExpect(status().isOk())
            .andDo(
                document(
                    "comma-delete",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        parameterWithName("commaId").description("삭제할 회고 아이디")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("삭제된 회고 아이디")
                    )
                ));
    }

    private List<CommaDetailResponse> createTestData() {
        List<CommentDetailResponse> comments1 = new ArrayList<>();
        comments1.add(new CommentDetailResponse(1L, "username1", 1L, "content1"));
        Long commaId1 = 1L;

        CommaDetailResponse commaDetailResponse1 = new CommaDetailResponse(
            commaId1, "title1", "content1", "username1", 1L, LocalDateTime.of(2022, 12, 27, 15, 13),2, comments1);


        List<CommentDetailResponse> comments2 = new ArrayList<>();
        comments2.add(new CommentDetailResponse(2L, "username2", 2L, "content2"));
        Long commaId2 = 2L;

        CommaDetailResponse commaDetailResponse2 = new CommaDetailResponse(
            commaId2, "title2", "content2", "username2", 2L, LocalDateTime.of(2022, 12, 28, 15, 13),3, comments2);

        List<CommaDetailResponse> commaDetailResponses = new ArrayList<>();
        commaDetailResponses.add(commaDetailResponse1);
        commaDetailResponses.add(commaDetailResponse2);
        return commaDetailResponses;
    }
}
