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
import commaproject.be.commaserver.service.CommentService;
import commaproject.be.commaserver.service.dto.CommentDetailResponse;
import commaproject.be.commaserver.service.dto.CommentRequest;
import commaproject.be.commaserver.service.dto.CommentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith({RestDocumentationExtension.class})
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService commentService;

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
    @DisplayName("댓글 생성 성공")
    void create_comment_success() throws Exception {

        // given
        Long commaId = 1L;
        Long commentId = 1L;
        CommentRequest commentRequest = new CommentRequest(commaId, "댓글 본문1", "username1", 1L);
        CommentResponse commentResponse = new CommentResponse(commentId);

        when(commentService.create(commaId, commentRequest)).thenReturn(commentResponse);

        // when
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.post("/api/commas/{commaId}/comments", commaId)
                .content(objectMapper
                    .writeValueAsString(commentRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result
            .andExpect(status().isOk())
            .andDo(
                document(
                    "create-comment",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        parameterWithName("commaId").description("생성할 댓글이 있는 회고 아이디")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("생성된 댓글 아이디")
                    )
                ));
    }

    @Test
    @DisplayName("댓글 수정 성공")
    void update_comment_success() throws Exception {

        // given
        Long commaId = 1L;
        Long commentId = 1L;
        CommentRequest commentRequest = new CommentRequest(commaId, "댓글 본문1", "username1", 1L);
        CommentDetailResponse commentDetailResponse = new CommentDetailResponse(
            commentId,
            commentRequest.getUsername(),
            commentRequest.getUserId(),
            commentRequest.getContent()
        );

        when(commentService.update(commaId, commentId, commentRequest)).thenReturn(commentDetailResponse);

        // when
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.put("/api/commas/{commaId}/comments/{commentId}", commaId, commentId)
                .content(objectMapper
                    .writeValueAsString(commentRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result
            .andExpect(status().isOk())
            .andDo(
                document(
                    "update-comment",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        parameterWithName("commaId").description("수정할 댓글이 있는 회고 아이디"),
                        parameterWithName("commentId").description("수정할 댓글 아이디")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("댓글 아이디"),
                        fieldWithPath("data.username").type(JsonFieldType.STRING).description("수정한 작성자 닉네임"),
                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("수정한 작성자 아이디"),
                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("수정된 댓글 내용")
                    )
                ));
    }

    @Test
    @DisplayName("댓글 삭제 성공")
    void delete_comment_success() throws Exception {

        // given
        Long commaId = 1L;
        Long commentId = 1L;
        CommentResponse commentResponse = new CommentResponse(commentId);

        when(commentService.delete(commaId, commentId)).thenReturn(commentResponse);

        // when
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.delete("/api/commas/{commaId}/comments/{commentId}", commaId, commentId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result
            .andExpect(status().isOk())
            .andDo(
                document(
                    "delete-comment",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        parameterWithName("commaId").description("삭제할 댓글이 있는 회고 아이디"),
                        parameterWithName("commentId").description("삭제할 댓글 아이디")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("삭제된 댓글 아이디")
                    )
                ));
    }
}
