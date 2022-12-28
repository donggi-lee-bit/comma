package commaproject.be.commaserver.controller;

import commaproject.be.commaserver.common.BaseResponse;
import commaproject.be.commaserver.service.CommaService;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommaController {

    private final CommaService commaService;

    @GetMapping("/api/commas/{commaId}")
    public BaseResponse<CommaDetailResponse> readOne(@PathVariable Long commaId) {
        CommaDetailResponse commaDetailResponse = commaService.readOne(commaId);
        return new BaseResponse<>("200", "OK", commaDetailResponse);
    }

    @GetMapping("/api/commas")
    public BaseResponse<List<CommaDetailResponse>> readAll() {
        List<CommaDetailResponse> commaDetailResponses = commaService.readAll();
        return new BaseResponse<>("200", "OK", commaDetailResponses);
    }
}
