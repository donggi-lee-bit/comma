package commaproject.be.commaserver.controller;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import commaproject.be.commaserver.common.response.BaseResponse;
import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.domain.user.User;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommaPaginatedResponse;
import commaproject.be.commaserver.service.dto.CommaRequest;
import commaproject.be.commaserver.service.dto.CommaResponse;
import commaproject.be.commaserver.service.dto.CommentDetailResponse;
import java.time.LocalDateTime;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.ResultActions;

@ExtendWith({RestDocumentationExtension.class})
@WebMvcTest(CommaController.class)
class CommaControllerTest extends InitControllerTest {

    @Test
    @DisplayName("특정 회고 조회 성공")
    void read_comma_success() throws Exception {
        List<CommentDetailResponse> comments = createCommentTestData();
        Long commaId = 1L;
        CommaDetailResponse commaDetailResponse = new CommaDetailResponse(
            commaId, "title1", "content1", 0, "username1", 1L, LocalDateTime.of(2022, 12, 27, 15, 13),1L, comments);
        BaseResponse<CommaDetailResponse> baseResponse = new BaseResponse<>("200", "OK",
            commaDetailResponse);

        when(commaService.readOne(commaId)).thenReturn(commaDetailResponse);

        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/commas/{commaId}", commaId)
                .content(objectMapper
                    .registerModule(new JavaTimeModule())
                    .writeValueAsString(baseResponse))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

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
                        fieldWithPath("data.postLikeCount").type(JsonFieldType.NUMBER).description("좋아요 개수"),
                        fieldWithPath("data.comments[].id").type(JsonFieldType.NUMBER).description("댓글 아이디"),
                        fieldWithPath("data.comments[].userId").type(JsonFieldType.NUMBER).description("댓글 작성자 아이디"),
                        fieldWithPath("data.comments[].username").type(JsonFieldType.STRING).description("댓글 작성자"),
                        fieldWithPath("data.comments[].content").type(JsonFieldType.STRING).description("댓글 내용"),
                        fieldWithPath("data.comments[].createdAt").type(JsonFieldType.STRING).description("댓글 생성 시간"),
                        fieldWithPath("data.comments[].lastModifiedAt").type(JsonFieldType.STRING).description("댓글 수정 시간")
                    )
            ));
    }

    @Test
    @DisplayName("전체 회고 조회 성공")
    void read_all_comma_success() throws Exception {
        int currentPage = 0;
        int lastPage = 1;
        int pageSize = 2;
        List<CommaDetailResponse> commaDetailResponses = createCommaTestData();
        CommaPaginatedResponse commaPaginatedResponse = new CommaPaginatedResponse(currentPage, pageSize,
            lastPage, commaDetailResponses);
        BaseResponse<CommaPaginatedResponse> baseResponse = new BaseResponse<>("200", "OK",
            commaPaginatedResponse);
        PageRequest pageRequest = PageRequest.of(currentPage, pageSize);
        when(commaService.readAll(pageRequest)).thenReturn(commaPaginatedResponse);

        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.get("/api/commas")
                .queryParam("page", "0")
                .queryParam("size", "2")
                .content(objectMapper
                    .registerModule(new JavaTimeModule())
                    .writeValueAsString(baseResponse))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

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
                ));
    }

    @Test
    @DisplayName("회고 생성 성공")
    void create_comma_success() throws Exception {
        Long userId = 1L;
        Long commaId = 1L;
        CommaRequest commaRequest = new CommaRequest("title1", "content1");
        CommaResponse commaResponse = new CommaResponse(commaId);

        when(commaService.create(userId, commaRequest)).thenReturn(commaResponse);

        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.post("/api/commas")
                .header("Authorization",
                    "Bearer " + jwtProvider.generateAccessToken(userId))
                .content(objectMapper
                    .writeValueAsString(commaRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

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
    @DisplayName("특정 회고 수정 성공")
    void update_comma_success() throws Exception {
        Long commaId = 1L;
        Long userId = 1L;
        CommaRequest commaRequest = new CommaRequest("title1", "content1");

        List<CommentDetailResponse> comments = createCommentTestData();

        CommaDetailResponse commaDetailResponse = new CommaDetailResponse(commaId, "title1", "content1", 0, "username1", userId, LocalDateTime.of(2022, 12, 28, 15, 30), 1L, comments);

        when(commaService.update(userId, commaId, commaRequest)).thenReturn(commaDetailResponse);

        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.put("/api/commas/{commaId}", commaId)
                .header("Authorization",
                    "Bearer " + jwtProvider.generateAccessToken(userId))
                .content(objectMapper
                    .writeValueAsString(commaRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

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
                        fieldWithPath("data.postLikeCount").type(JsonFieldType.NUMBER).description("좋아요 개수"),
                        fieldWithPath("data.comments[].id").type(JsonFieldType.NUMBER).description("댓글 아이디"),
                        fieldWithPath("data.comments[].userId").type(JsonFieldType.NUMBER).description("댓글 작성자 아이디"),
                        fieldWithPath("data.comments[].username").type(JsonFieldType.STRING).description("댓글 작성자"),
                        fieldWithPath("data.comments[].content").type(JsonFieldType.STRING).description("댓글 내용"),
                        fieldWithPath("data.comments[].createdAt").type(JsonFieldType.STRING).description("댓글 생성 시간"),
                        fieldWithPath("data.comments[].lastModifiedAt").type(JsonFieldType.STRING).description("댓글 수정 시간")
                    )
                ));
    }

    @Test
    @DisplayName("회고 삭제 성공")
    void delete_comma_success() throws Exception {
        Long commaId = 1L;
        Long loginUserId = 1L;
        User user = User.from("username1", "test@test.com", "test.jpg");
        Comma comma = Comma.from("title1", "content1", user);
        ReflectionTestUtils.setField(comma, "id", commaId);
        when(commaService.remove(loginUserId, commaId)).thenReturn(comma);

        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.delete("/api/commas/{commaId}", commaId)
                .header("Authorization",
                    "Bearer " + jwtProvider.generateAccessToken(loginUserId))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

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
}
