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
import commaproject.be.commaserver.service.dto.CommaRequest;
import commaproject.be.commaserver.service.dto.CommaResponse;
import commaproject.be.commaserver.service.dto.CommentDetailResponse;
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
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.ResultActions;

@ExtendWith({RestDocumentationExtension.class})
@WebMvcTest(CommaController.class)
class CommaControllerTest extends InitControllerTest {

    @Test
    @DisplayName("?????? ?????? ?????? ??????")
    void read_comma_success() throws Exception {

        // given
        List<CommentDetailResponse> comments = createCommentTestData();
        Long commaId = 1L;
        CommaDetailResponse commaDetailResponse = new CommaDetailResponse(
            commaId, "title1", "content1", "username1", 1L, LocalDateTime.of(2022, 12, 27, 15, 13),1L, comments);
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
                        parameterWithName("commaId").description("?????? ?????????")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????"),
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.username").type(JsonFieldType.STRING).description("?????? ?????????"),
                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("?????? ????????? ?????????"),
                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("?????? ????????? ??????"),
                        fieldWithPath("data.postLikeCount").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("data.comments[].id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                        fieldWithPath("data.comments[].userId").type(JsonFieldType.NUMBER).description("?????? ????????? ?????????"),
                        fieldWithPath("data.comments[].username").type(JsonFieldType.STRING).description("?????? ?????????"),
                        fieldWithPath("data.comments[].content").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.comments[].createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                        fieldWithPath("data.comments[].lastModifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                    )
            ));
    }

    @Test
    @DisplayName("?????? ?????? ?????? ??????")
    void read_all_comma_success() throws Exception {

        // given
        List<CommaDetailResponse> commaDetailResponses = createCommaTestData();

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
                        fieldWithPath("code").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????"),
                        fieldWithPath("data[].id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                        fieldWithPath("data[].title").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data[].content").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data[].username").type(JsonFieldType.STRING).description("?????? ?????????"),
                        fieldWithPath("data[].userId").type(JsonFieldType.NUMBER).description("?????? ????????? ?????????"),
                        fieldWithPath("data[].postLikeCount").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("?????? ????????? ??????"),
                        fieldWithPath("data[].comments[].id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                        fieldWithPath("data[].comments[].userId").type(JsonFieldType.NUMBER).description("?????? ????????? ?????????"),
                        fieldWithPath("data[].comments[].username").type(JsonFieldType.STRING).description("?????? ?????????"),
                        fieldWithPath("data[].comments[].content").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data[].comments[].createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                        fieldWithPath("data[].comments[].lastModifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                    )
                ));
    }

    @Test
    @DisplayName("?????? ?????? ??????")
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
                        fieldWithPath("code").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????"),
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("????????? ?????? ?????????")
                    )
                ));
    }

    @Test
    @DisplayName("?????? ?????? ?????? ??????")
    void update_comma_success() throws Exception {

        // given
        Long commaId = 1L;
        Long userId = 1L;
        CommaRequest commaRequest = new CommaRequest("title1", "content1");

        List<CommentDetailResponse> comments = createCommentTestData();

        CommaDetailResponse commaDetailResponse = new CommaDetailResponse(commaId, "title1", "content1", "username1", userId, LocalDateTime.of(2022, 12, 28, 15, 30), 1L, comments);

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
                        parameterWithName("commaId").description("????????? ?????? ?????????")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????"),
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                        fieldWithPath("data.content").type(JsonFieldType.STRING).description("????????? ?????? ??????"),
                        fieldWithPath("data.username").type(JsonFieldType.STRING).description("????????? ?????? ?????????"),
                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("????????? ?????? ????????? ?????????"),
                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("?????? ????????? ??????"),
                        fieldWithPath("data.postLikeCount").type(JsonFieldType.NUMBER).description("????????? ??????"),
                        fieldWithPath("data.comments[].id").type(JsonFieldType.NUMBER).description("?????? ?????????"),
                        fieldWithPath("data.comments[].userId").type(JsonFieldType.NUMBER).description("?????? ????????? ?????????"),
                        fieldWithPath("data.comments[].username").type(JsonFieldType.STRING).description("?????? ?????????"),
                        fieldWithPath("data.comments[].content").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("data.comments[].createdAt").type(JsonFieldType.STRING).description("?????? ?????? ??????"),
                        fieldWithPath("data.comments[].lastModifiedAt").type(JsonFieldType.STRING).description("?????? ?????? ??????")
                    )
                ));
    }

    @Test
    @DisplayName("?????? ?????? ??????")
    void delete_comma_success() throws Exception {

        // given
        Long commaId = 1L;
        Long loginUserId = 1L;
        User user = User.from("username1", "test@test.com", "test.jpg");
        Comma comma = Comma.from("title1", "content1", user);
        ReflectionTestUtils.setField(comma, "id", commaId);

        when(commaService.remove(loginUserId, commaId)).thenReturn(comma);

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
                        parameterWithName("commaId").description("????????? ?????? ?????????")
                    ),
                    responseFields(
                        fieldWithPath("code").type(JsonFieldType.STRING).description("?????? ??????"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("?????? ?????????"),
                        fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("????????? ?????? ?????????")
                    )
                ));
    }
}
