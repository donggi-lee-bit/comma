package commaproject.be.commaserver.controller;


import commaproject.be.commaserver.common.BaseResponse;
import commaproject.be.commaserver.service.SearchService;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.SearchConditionRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/api/commas/search")
    public BaseResponse<List<CommaDetailResponse>> searchByCondition(SearchConditionRequest searchConditionRequest) {
        List<CommaDetailResponse> commaDetailResponses = searchService.searchByCondition(searchConditionRequest);
        return new BaseResponse<>("200", "OK", commaDetailResponses);
    }
}
