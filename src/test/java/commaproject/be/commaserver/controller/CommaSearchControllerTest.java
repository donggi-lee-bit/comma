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
import commaproject.be.commaserver.service.dto.CommaPaginatedResponse;
import commaproject.be.commaserver.service.dto.CommaSearchConditionRequest;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageRequest;
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
        int currentPage = 0;
        int lastPage = 1;
        int pageSize = 2;
        List<CommaDetailResponse> commaDetailResponses = createCommaTestData();
        CommaPaginatedResponse commaPaginatedResponse = new CommaPaginatedResponse(currentPage, pageSize,
            lastPage, commaDetailResponses);
        BaseResponse<CommaPaginatedResponse> baseResponse = new BaseResponse<>("200", "OK",
            commaPaginatedResponse);
        LocalDate date = LocalDate.of(2022, 12, 28);
        String username = "donggi";
        CommaSearchConditionRequest commaSearchConditionRequest = new CommaSearchConditionRequest(date, username);
        PageRequest pageRequest = PageRequest.of(0, 2);

        when(commaSearchService.searchByCondition(commaSearchConditionRequest, pageRequest)).thenReturn(commaPaginatedResponse);

        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/commas")
                .queryParam("type", "userdate")
                .queryParam("date", "20221228")
                .queryParam("username", commaSearchConditionRequest.getUsername())
                .queryParam("page", "0")
                .queryParam("size", "2")
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
                        fieldWithPath("data.currentPage").type(JsonFieldType.NUMBER).description("현재 페이지"),
                        fieldWithPath("data.pageSize").type(JsonFieldType.NUMBER).description("요청한 오프셋 사이즈"),
                        fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 개수"),
                        fieldWithPath("data.commaDetailResponses[].id").type(JsonFieldType.NUMBER).description("회고 아이디"),
                        fieldWithPath("data.commaDetailResponses[].title").type(JsonFieldType.STRING).description("회고 제목"),
                        fieldWithPath("data.commaDetailResponses[].content").type(JsonFieldType.STRING).description("회고 내용"),
                        fieldWithPath("data.commaDetailResponses[].username").type(JsonFieldType.STRING).description("회고 작성자"),
                        fieldWithPath("data.commaDetailResponses[].userId").type(JsonFieldType.NUMBER).description("회고 작성자 아이디"),
                        fieldWithPath("data.commaDetailResponses[].postLikeCount").type(JsonFieldType.NUMBER).description("좋아요 개수"),
                        fieldWithPath("data.commaDetailResponses[].createdAt").type(JsonFieldType.STRING).description("회고 작성된 시간"),
                        fieldWithPath("data.commaDetailResponses[].comments[].id").type(JsonFieldType.NUMBER).description("댓글 아이디"),
                        fieldWithPath("data.commaDetailResponses[].comments[].userId").type(JsonFieldType.NUMBER).description("댓글 작성자 아이디"),
                        fieldWithPath("data.commaDetailResponses[].comments[].username").type(JsonFieldType.STRING).description("댓글 작성자"),
                        fieldWithPath("data.commaDetailResponses[].comments[].content").type(JsonFieldType.STRING).description("댓글 내용"),
                        fieldWithPath("data.commaDetailResponses[].comments[].createdAt").type(JsonFieldType.STRING).description("댓글 생성 시간"),
                        fieldWithPath("data.commaDetailResponses[].comments[].lastModifiedAt").type(JsonFieldType.STRING).description("댓글 수정 시간")
                    )
                )
            );
    }

    @DisplayName("특정 사용자가 쓴 글 조회 성공")
    @Test
    void read_search_user() throws Exception {
        int currentPage = 0;
        int lastPage = 1;
        int pageSize = 2;
        List<CommaDetailResponse> commaDetailResponses = createCommaTestData();
        CommaPaginatedResponse commaPaginatedResponse = new CommaPaginatedResponse(currentPage, pageSize,
            lastPage, commaDetailResponses);
        BaseResponse<CommaPaginatedResponse> baseResponse = new BaseResponse<>("200", "OK",
            commaPaginatedResponse);
        LocalDate date = null;
        String username = "donggi";
        CommaSearchConditionRequest commaSearchConditionRequest = new CommaSearchConditionRequest(date, username);
        PageRequest pageRequest = PageRequest.of(0, 2);

        when(commaSearchService.searchByCondition(commaSearchConditionRequest, pageRequest)).thenReturn(commaPaginatedResponse);

        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/commas")
                .queryParam("type", "user")
                .queryParam("username", commaSearchConditionRequest.getUsername())
                .queryParam("page", "0")
                .queryParam("size", "2")
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
                        fieldWithPath("data.currentPage").type(JsonFieldType.NUMBER).description("현재 페이지"),
                        fieldWithPath("data.pageSize").type(JsonFieldType.NUMBER).description("요청한 오프셋 사이즈"),
                        fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 개수"),
                        fieldWithPath("data.commaDetailResponses[].id").type(JsonFieldType.NUMBER).description("회고 아이디"),
                        fieldWithPath("data.commaDetailResponses[].title").type(JsonFieldType.STRING).description("회고 제목"),
                        fieldWithPath("data.commaDetailResponses[].content").type(JsonFieldType.STRING).description("회고 내용"),
                        fieldWithPath("data.commaDetailResponses[].username").type(JsonFieldType.STRING).description("회고 작성자"),
                        fieldWithPath("data.commaDetailResponses[].userId").type(JsonFieldType.NUMBER).description("회고 작성자 아이디"),
                        fieldWithPath("data.commaDetailResponses[].postLikeCount").type(JsonFieldType.NUMBER).description("좋아요 개수"),
                        fieldWithPath("data.commaDetailResponses[].createdAt").type(JsonFieldType.STRING).description("회고 작성된 시간"),
                        fieldWithPath("data.commaDetailResponses[].comments[].id").type(JsonFieldType.NUMBER).description("댓글 아이디"),
                        fieldWithPath("data.commaDetailResponses[].comments[].userId").type(JsonFieldType.NUMBER).description("댓글 작성자 아이디"),
                        fieldWithPath("data.commaDetailResponses[].comments[].username").type(JsonFieldType.STRING).description("댓글 작성자"),
                        fieldWithPath("data.commaDetailResponses[].comments[].content").type(JsonFieldType.STRING).description("댓글 내용"),
                        fieldWithPath("data.commaDetailResponses[].comments[].createdAt").type(JsonFieldType.STRING).description("댓글 생성 시간"),
                        fieldWithPath("data.commaDetailResponses[].comments[].lastModifiedAt").type(JsonFieldType.STRING).description("댓글 수정 시간")
                    )
                )
            );
    }

    @DisplayName("특정 날짜에 쓴 글 조회 성공")
    @Test
    void read_search_date() throws Exception {
        int currentPage = 0;
        int lastPage = 1;
        int pageSize = 2;
        List<CommaDetailResponse> commaDetailResponses = createCommaTestData();
        CommaPaginatedResponse commaPaginatedResponse = new CommaPaginatedResponse(currentPage, pageSize,
            lastPage, commaDetailResponses);
        BaseResponse<CommaPaginatedResponse> baseResponse = new BaseResponse<>("200", "OK",
            commaPaginatedResponse);
        LocalDate date = LocalDate.of(2022, 12, 28);
        String username = null;
        CommaSearchConditionRequest commaSearchConditionRequest = new CommaSearchConditionRequest(date, username);
        PageRequest pageRequest = PageRequest.of(0, 2);

        when(commaSearchService.searchByCondition(commaSearchConditionRequest, pageRequest)).thenReturn(commaPaginatedResponse);

        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/commas")
                .queryParam("type", "date")
                .queryParam("date", "20221228")
                .queryParam("page", "0")
                .queryParam("size", "2")
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
                        fieldWithPath("data.currentPage").type(JsonFieldType.NUMBER).description("현재 페이지"),
                        fieldWithPath("data.pageSize").type(JsonFieldType.NUMBER).description("요청한 오프셋 사이즈"),
                        fieldWithPath("data.totalPages").type(JsonFieldType.NUMBER).description("전체 페이지 개수"),
                        fieldWithPath("data.commaDetailResponses[].id").type(JsonFieldType.NUMBER).description("회고 아이디"),
                        fieldWithPath("data.commaDetailResponses[].title").type(JsonFieldType.STRING).description("회고 제목"),
                        fieldWithPath("data.commaDetailResponses[].content").type(JsonFieldType.STRING).description("회고 내용"),
                        fieldWithPath("data.commaDetailResponses[].username").type(JsonFieldType.STRING).description("회고 작성자"),
                        fieldWithPath("data.commaDetailResponses[].userId").type(JsonFieldType.NUMBER).description("회고 작성자 아이디"),
                        fieldWithPath("data.commaDetailResponses[].postLikeCount").type(JsonFieldType.NUMBER).description("좋아요 개수"),
                        fieldWithPath("data.commaDetailResponses[].createdAt").type(JsonFieldType.STRING).description("회고 작성된 시간"),
                        fieldWithPath("data.commaDetailResponses[].comments[].id").type(JsonFieldType.NUMBER).description("댓글 아이디"),
                        fieldWithPath("data.commaDetailResponses[].comments[].userId").type(JsonFieldType.NUMBER).description("댓글 작성자 아이디"),
                        fieldWithPath("data.commaDetailResponses[].comments[].username").type(JsonFieldType.STRING).description("댓글 작성자"),
                        fieldWithPath("data.commaDetailResponses[].comments[].content").type(JsonFieldType.STRING).description("댓글 내용"),
                        fieldWithPath("data.commaDetailResponses[].comments[].createdAt").type(JsonFieldType.STRING).description("댓글 생성 시간"),
                        fieldWithPath("data.commaDetailResponses[].comments[].lastModifiedAt").type(JsonFieldType.STRING).description("댓글 수정 시간")
                    )
                )
            );
    }
}
