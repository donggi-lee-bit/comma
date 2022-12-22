package commaproject.be.commaserver.comma;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import commaproject.be.commaserver.controller.CommaController;
import commaproject.be.commaserver.service.CommaService;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommentResponse;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(CommaController.class)
@DisplayName("API /api/commas/* 컨트롤러 계층 단위 테스트")
class CommaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommaService commaService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 특정_회고_조회_성공() throws Exception {
        List<CommentResponse> comments = new ArrayList<>();
        comments.add(new CommentResponse(1L, "username1", 1L, "content1"));

        Long commaId = 1L;
        CommaDetailResponse commaDetailResponse = new CommaDetailResponse(
            commaId, "title1", "content1", "username1", 1L, 1, comments);
        when(commaService.readOne(commaId)).thenReturn(commaDetailResponse);

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/commas/{commaId}", commaId)
            );

        resultActions.andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content()
                .string(objectMapper.writeValueAsString(commaDetailResponse)));
    }
}
