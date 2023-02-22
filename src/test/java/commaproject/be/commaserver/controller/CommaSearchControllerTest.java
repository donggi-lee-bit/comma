package commaproject.be.commaserver.controller;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import commaproject.be.commaserver.common.response.BaseResponse;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommaSearchConditionRequest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

@ExtendWith({RestDocumentationExtension.class})
@WebMvcTest(CommaSearchController.class)
class CommaSearchControllerTest extends InitControllerTest {

    @DisplayName("특정 날짜, 특정 유저가 쓴 글 조회 성공")
    @Test
    void read_search_date_user() throws Exception {
        List<CommaDetailResponse> commaDetailResponses = createCommaTestData();

        BaseResponse<List<CommaDetailResponse>> baseResponse = new BaseResponse<>("200", "OK",
            commaDetailResponses);

        // 2022-12-28T12:00
        LocalDateTime date = LocalDateTime.of(2022, 12, 28, 12, 0, 0);
        String username = "donggi";
        CommaSearchConditionRequest commaSearchConditionRequest = new CommaSearchConditionRequest(date, username);

        when(commaSearchService.searchByCondition(commaSearchConditionRequest)).thenReturn(commaDetailResponses);

        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/commas")
                .queryParam("type", "userdate")
                .queryParam("date", "20221228120000")
                .queryParam("username", commaSearchConditionRequest.getUsername())
                .content(objectMapper
                    .registerModule(new JavaTimeModule())
                    .writeValueAsString(baseResponse))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

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
                        fieldWithPath("data[].postLikeCount").type(JsonFieldType.NUMBER).description("좋아요 개수"),
                        fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("회고 작성된 시간"),
                        fieldWithPath("data[].comments[].id").type(JsonFieldType.NUMBER).description("댓글 아이디"),
                        fieldWithPath("data[].comments[].userId").type(JsonFieldType.NUMBER).description("댓글 작성자 아이디"),
                        fieldWithPath("data[].comments[].username").type(JsonFieldType.STRING).description("댓글 작성자"),
                        fieldWithPath("data[].comments[].content").type(JsonFieldType.STRING).description("댓글 내용"),
                        fieldWithPath("data[].comments[].createdAt").type(JsonFieldType.STRING).description("댓글 생성 시간"),
                        fieldWithPath("data[].comments[].lastModifiedAt").type(JsonFieldType.STRING).description("댓글 수정 시간")
                    )
                )
            );
    }

    @DisplayName("특정 사용자가 쓴 글 조회 성공")
    @Test
    void read_search_user() throws Exception {
        List<CommaDetailResponse> commaDetailResponses = createCommaTestData();

        BaseResponse<List<CommaDetailResponse>> baseResponse = new BaseResponse<>("200", "OK",
            commaDetailResponses);

        LocalDateTime date = null;
        String username = "donggi";
        CommaSearchConditionRequest commaSearchConditionRequest = new CommaSearchConditionRequest(date, username);

        when(commaSearchService.searchByCondition(commaSearchConditionRequest)).thenReturn(commaDetailResponses);

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
                        fieldWithPath("data[].postLikeCount").type(JsonFieldType.NUMBER).description("좋아요 개수"),
                        fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("회고 작성된 시간"),
                        fieldWithPath("data[].comments[].id").type(JsonFieldType.NUMBER).description("댓글 아이디"),
                        fieldWithPath("data[].comments[].userId").type(JsonFieldType.NUMBER).description("댓글 작성자 아이디"),
                        fieldWithPath("data[].comments[].username").type(JsonFieldType.STRING).description("댓글 작성자"),
                        fieldWithPath("data[].comments[].content").type(JsonFieldType.STRING).description("댓글 내용"),
                        fieldWithPath("data[].comments[].createdAt").type(JsonFieldType.STRING).description("댓글 생성 시간"),
                        fieldWithPath("data[].comments[].lastModifiedAt").type(JsonFieldType.STRING).description("댓글 수정 시간")
                    )
                )
            );
    }

    @DisplayName("특정 날짜에 쓴 글 조회 성공")
    @Test
    void read_search_date() throws Exception {
        List<CommaDetailResponse> commaDetailResponses = createCommaTestData();

        BaseResponse<List<CommaDetailResponse>> baseResponse = new BaseResponse<>("200", "OK",
            commaDetailResponses);

        LocalDateTime date = LocalDateTime.of(2022, 12, 28, 12, 0);
        String username = null;
        CommaSearchConditionRequest commaSearchConditionRequest = new CommaSearchConditionRequest(date, username);

        when(commaSearchService.searchByCondition(commaSearchConditionRequest)).thenReturn(commaDetailResponses);

        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/commas")
                .queryParam("type", "date")
                .queryParam("date", "20221228120000")
                .content(objectMapper
                    .registerModule(new JavaTimeModule())
                    .writeValueAsString(baseResponse))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

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
                        fieldWithPath("data[].postLikeCount").type(JsonFieldType.NUMBER).description("좋아요 개수"),
                        fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("회고 작성된 시간"),
                        fieldWithPath("data[].comments[].id").type(JsonFieldType.NUMBER).description("댓글 아이디"),
                        fieldWithPath("data[].comments[].userId").type(JsonFieldType.NUMBER).description("댓글 작성자 아이디"),
                        fieldWithPath("data[].comments[].username").type(JsonFieldType.STRING).description("댓글 작성자"),
                        fieldWithPath("data[].comments[].content").type(JsonFieldType.STRING).description("댓글 내용"),
                        fieldWithPath("data[].comments[].createdAt").type(JsonFieldType.STRING).description("댓글 생성 시간"),
                        fieldWithPath("data[].comments[].lastModifiedAt").type(JsonFieldType.STRING).description("댓글 수정 시간")
                    )
                )
            );
    }
}
