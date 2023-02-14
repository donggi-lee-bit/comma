package commaproject.be.commaserver.controller;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import commaproject.be.commaserver.service.dto.PostLikeRequest;
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
@WebMvcTest(PostLikeController.class)
public class PostLikeControllerTest extends InitControllerTest{

    @Test
    @DisplayName("사용자가 특정 회고 게시글 좋아요 성공")
    void user_click_like_success() throws Exception {
        Long userId = 1L;
        Long commaId = 1L;
        PostLikeRequest postLikeRequest = new PostLikeRequest(true);

        ResultActions result = mockMvc.perform(
            RestDocumentationRequestBuilders.post("/api/likes/{commaId}/like", commaId)
                .header("Authorization",
                    "Bearer " + jwtProvider.generateAccessToken(userId))
                .content(objectMapper
                    .writeValueAsString(postLikeRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        result
            .andExpect(status().isOk())
            .andDo(
                document(
                    "post-like",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    pathParameters(
                        parameterWithName("commaId").description("수정된 회고 아이디")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                        fieldWithPath("data").type(JsonFieldType.NULL).description("응답 데이터")
                    )
                )
            );
    }
}
