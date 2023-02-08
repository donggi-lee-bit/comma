package commaproject.be.commaserver.controller;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import commaproject.be.commaserver.common.BaseResponse;
import commaproject.be.commaserver.service.CommaSearchService;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommaSearchConditionRequest;
import commaproject.be.commaserver.service.dto.CommentDetailResponse;
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
@WebMvcTest(CommaSearchController.class)
class CommaSearchControllerTest extends InitContollerTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommaSearchService commaSearchService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
        RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply(documentationConfiguration(restDocumentation))
            .alwaysDo(document("{method-name}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
            .build();
    }

    @DisplayName("특정 날짜, 특정 유저가 쓴 글 조회 성공")
    @Test
    void read_search_date_user() throws Exception {

        // given
        List<CommaDetailResponse> commaDetailResponses = createTestData();

        BaseResponse<List<CommaDetailResponse>> baseResponse = new BaseResponse<>("200", "OK",
            commaDetailResponses);

        String date = "20221228";
        String username = "donggi";
        CommaSearchConditionRequest commaSearchConditionRequest = new CommaSearchConditionRequest(date, username);

        when(commaSearchService.searchByCondition(commaSearchConditionRequest)).thenReturn(commaDetailResponses);

        // when
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/commas")
                .queryParam("type", "userdate")
                .queryParam("date", commaSearchConditionRequest.getDate())
                .queryParam("username", commaSearchConditionRequest.getUsername())
                .content(objectMapper
                    .registerModule(new JavaTimeModule())
                    .writeValueAsString(baseResponse))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
            .andExpect(status().isOk())
            .andDo(
                document(
                    "read-search-date-user",
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
                )
            );
    }

    @DisplayName("특정 사용자가 쓴 글 조회 성공")
    @Test
    void read_search_user() throws Exception {

        // given
        List<CommaDetailResponse> commaDetailResponses = createTestData();

        BaseResponse<List<CommaDetailResponse>> baseResponse = new BaseResponse<>("200", "OK",
            commaDetailResponses);

        String date = null;
        String username = "donggi";
        CommaSearchConditionRequest commaSearchConditionRequest = new CommaSearchConditionRequest(date, username);

        when(commaSearchService.searchByCondition(commaSearchConditionRequest)).thenReturn(commaDetailResponses);

        // when
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/commas")
                .queryParam("type", "user")
                .queryParam("username", commaSearchConditionRequest.getUsername())
                .content(objectMapper
                    .registerModule(new JavaTimeModule())
                    .writeValueAsString(baseResponse))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
            .andExpect(status().isOk())
            .andDo(
                document(
                    "read-search-user",
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
                )
            );
    }

    @DisplayName("특정 날짜에 쓴 글 조회 성공")
    @Test
    void read_search_date() throws Exception {

        // given
        List<CommaDetailResponse> commaDetailResponses = createTestData();

        BaseResponse<List<CommaDetailResponse>> baseResponse = new BaseResponse<>("200", "OK",
            commaDetailResponses);

        String date = "20221228";
        String username = null;
        CommaSearchConditionRequest commaSearchConditionRequest = new CommaSearchConditionRequest(date, username);

        when(commaSearchService.searchByCondition(commaSearchConditionRequest)).thenReturn(commaDetailResponses);

        // when
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/commas")
                .queryParam("type", "date")
                .queryParam("date", commaSearchConditionRequest.getDate())
                .content(objectMapper
                    .registerModule(new JavaTimeModule())
                    .writeValueAsString(baseResponse))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result
            .andExpect(status().isOk())
            .andDo(
                document(
                    "read-search-date",
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
                )
            );
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

        List<CommentDetailResponse> comments3 = new ArrayList<>();
        comments3.add(new CommentDetailResponse(3L, "username3", 3L, "content3"));
        Long commaId3 = 3L;

        CommaDetailResponse commaDetailResponse3 = new CommaDetailResponse(
            commaId3, "title3", "content3", "username3", 3L, LocalDateTime.of(2022, 12, 28, 15, 14),3, comments3);


        List<CommaDetailResponse> commaDetailResponses = new ArrayList<>();
        commaDetailResponses.add(commaDetailResponse1);
        commaDetailResponses.add(commaDetailResponse2);
        commaDetailResponses.add(commaDetailResponse3);
        return commaDetailResponses;
    }
}
