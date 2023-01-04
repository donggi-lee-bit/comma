package commaproject.be.commaserver.controller;


import commaproject.be.commaserver.common.BaseResponse;
import commaproject.be.commaserver.service.SearchService;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/api/commas")
    public BaseResponse<List<CommaDetailResponse>> readSearchDate(@RequestParam String date) {
        List<CommaDetailResponse> commaDetailResponses = searchService.readUsingDate(date);
        return new BaseResponse<>("200", "OK", commaDetailResponses);
    }
}
