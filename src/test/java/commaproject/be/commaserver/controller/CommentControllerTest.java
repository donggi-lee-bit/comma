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

import commaproject.be.commaserver.service.dto.CommentDetailResponse;
import commaproject.be.commaserver.service.dto.CommentRequest;
import commaproject.be.commaserver.service.dto.CommentResponse;
import java.time.LocalDateTime;
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
@WebMvcTest(CommentController.class)
class CommentControllerTest extends InitControllerTest {

    @Test
    @DisplayName("댓글 생성 성공")
    void create_comment_success() throws Exception {

        // given
        Long commaId = 1L;
        Long commentId = 1L;
        Long loginUserId = 1L;
        CommentRequest commentRequest = new CommentRequest("댓글 본문1");
        CommentResponse commentResponse = new CommentResponse(commentId);

        when(commentService.create(loginUserId, commaId, commentRequest)).thenReturn(commentResponse);

        // when
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.post("/api/commas/{commaId}/comments", commaId)
                .header("Authorization",
                    "Bearer " + jwtProvider.generateAccessToken(loginUserId))
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
        Long loginUserId = 1L;
        CommentRequest commentRequest = new CommentRequest("댓글 본문1");
        CommentDetailResponse commentDetailResponse = new CommentDetailResponse(1L, 1L, "content1",
            LocalDateTime.of(2023, 2, 9, 13, 26),
            LocalDateTime.of(2023, 2, 9, 13, 27)
        );

        when(commentService.update(loginUserId, commaId, commentId, commentRequest)).thenReturn(commentDetailResponse);

        // when
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.put("/api/commas/{commaId}/comments/{commentId}", commaId, commentId)
                .header("Authorization",
                    "Bearer " + jwtProvider.generateAccessToken(loginUserId))
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
                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("수정한 작성자 아이디"),
                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("수정된 댓글 내용"),
                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("댓글 생성 시간"),
                        fieldWithPath("data.lastModifiedAt").type(JsonFieldType.STRING).description("댓글 수정 시간")
                    )
                ));
    }

    @Test
    @DisplayName("댓글 삭제 성공")
    void delete_comment_success() throws Exception {

        // given
        Long commaId = 1L;
        Long commentId = 1L;
        Long loginUserId = 1L;
        CommentResponse commentResponse = new CommentResponse(commentId);

        when(commentService.delete(loginUserId, commaId, commentId)).thenReturn(commentResponse);

        // when
        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.delete("/api/commas/{commaId}/comments/{commentId}", commaId, commentId)
                .header("Authorization",
                    "Bearer " + jwtProvider.generateAccessToken(loginUserId))
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
