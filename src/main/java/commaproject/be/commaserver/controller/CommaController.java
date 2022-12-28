package commaproject.be.commaserver.controller;

import commaproject.be.commaserver.common.BaseResponse;
import commaproject.be.commaserver.service.dto.CommaResponse;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.CommaService;
import commaproject.be.commaserver.service.dto.CommaRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PostMapping("/api/commas")
    public BaseResponse<CommaResponse> update(@RequestBody CommaRequest commaRequest) {
        CommaResponse commaResponse = commaService.create(commaRequest);
        return new BaseResponse<>("200", "OK", commaResponse);
    }

    @PutMapping("/api/commas/{commaId}")
    public BaseResponse<CommaDetailResponse> update(@PathVariable Long commaId, @RequestBody CommaRequest commaRequest) {
        CommaDetailResponse updateCommaDetailResponse = commaService.update(commaId, commaRequest);
        return new BaseResponse<>("200", "OK", updateCommaDetailResponse);
    }
}
