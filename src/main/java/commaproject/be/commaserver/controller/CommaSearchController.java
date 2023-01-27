package commaproject.be.commaserver.controller;


import commaproject.be.commaserver.common.BaseResponse;
import commaproject.be.commaserver.service.CommaSearchService;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommaSearchConditionRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommaSearchController {

    private final CommaSearchService searchService;

    @GetMapping(path = "/api/commas", params = "type=date")
    public BaseResponse<List<CommaDetailResponse>> searchByDateCondition(
        CommaSearchConditionRequest commaSearchConditionRequest) {
        List<CommaDetailResponse> commaDetailResponses = searchService.searchByCondition(
            commaSearchConditionRequest);
        return new BaseResponse<>("200", "OK", commaDetailResponses);
    }

    @GetMapping(path = "/api/commas", params = "type=user")
    public BaseResponse<List<CommaDetailResponse>> searchByUserCondition(
        CommaSearchConditionRequest commaSearchConditionRequest) {
        List<CommaDetailResponse> commaDetailResponses = searchService.searchByCondition(
            commaSearchConditionRequest);
        return new BaseResponse<>("200", "OK", commaDetailResponses);
    }

    @GetMapping(path = "/api/commas", params = "type=userdate")
    public BaseResponse<List<CommaDetailResponse>> searchByUserDateCondition(
        CommaSearchConditionRequest commaSearchConditionRequest) {
        List<CommaDetailResponse> commaDetailResponses = searchService.searchByCondition(
            commaSearchConditionRequest);
        return new BaseResponse<>("200", "OK", commaDetailResponses);
    }
}

