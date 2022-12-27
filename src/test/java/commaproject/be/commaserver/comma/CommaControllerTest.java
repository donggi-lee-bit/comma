package commaproject.be.commaserver.comma;

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
import commaproject.be.commaserver.common.BaseResponse;
import commaproject.be.commaserver.controller.CommaController;
import commaproject.be.commaserver.service.CommaService;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommentResponse;
import java.util.ArrayList;
import java.util.List;
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
@WebMvcTest(CommaController.class)
@DisplayName("API /api/commas/* 컨트롤러 계층 단위 테스트")
class CommaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommaService commaService;

    private ObjectMapper objectMapper = new ObjectMapper();

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
    @DisplayName("commaId로 회고를 조회할 때 commaId가 존재한다면 테스트가 성공한다 ")
    void read_comma_success() throws Exception {

        // given
        List<CommentResponse> comments = new ArrayList<>();
        comments.add(new CommentResponse(1L, "username1", 1L, "content1"));
        Long commaId = 1L;
        CommaDetailResponse commaDetailResponse = new CommaDetailResponse(
            commaId, "title1", "content1", "username1", 1L, 1, comments);
        BaseResponse<CommaDetailResponse> baseResponse = new BaseResponse<>("200", "OK",
            commaDetailResponse);

        // when
        when(commaService.readOne(commaId)).thenReturn(commaDetailResponse);

        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/commas/{commaId}", commaId)
                .content(objectMapper.writeValueAsString(baseResponse))
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
                        fieldWithPath("data.likeCount").type(JsonFieldType.NUMBER).description("좋아요 개수"),
                        fieldWithPath("data.comments[].id").type(JsonFieldType.NUMBER).description("댓글 아이디"),
                        fieldWithPath("data.comments[].username").type(JsonFieldType.STRING).description("댓글 작성자"),
                        fieldWithPath("data.comments[].userId").type(JsonFieldType.NUMBER).description("댓글 작성자 아이디"),
                        fieldWithPath("data.comments[].content").type(JsonFieldType.STRING).description("댓글 내용")
                    )
            ));
    }
}
