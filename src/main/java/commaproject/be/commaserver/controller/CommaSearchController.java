package commaproject.be.commaserver.controller;


import static commaproject.be.commaserver.common.response.ResponseCodeAndMessage.SEARCH_BY_DATE_LOG_SUCCESS;
import static commaproject.be.commaserver.common.response.ResponseCodeAndMessage.SEARCH_BY_USER_DATE_LOG_SUCCESS;
import static commaproject.be.commaserver.common.response.ResponseCodeAndMessage.SEARCH_BY_USER_LOG_SUCCESS;

import commaproject.be.commaserver.common.response.BaseResponse;
import commaproject.be.commaserver.service.CommaSearchService;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommaSearchConditionRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommaSearchController {

    private final CommaSearchService searchService;

    @GetMapping(path = "/api/commas", params = "type=date")
    public BaseResponse<List<CommaDetailResponse>> searchByDateCondition(
        @ModelAttribute CommaSearchConditionRequest commaSearchConditionRequest, Pageable pageable) {
        List<CommaDetailResponse> commaDetailResponses = searchService.searchByDateCondition(
            commaSearchConditionRequest, pageable);
        return new BaseResponse<>(SEARCH_BY_DATE_LOG_SUCCESS, commaDetailResponses);
    }

    @GetMapping(path = "/api/commas", params = "type=user")
    public BaseResponse<List<CommaDetailResponse>> searchByUserCondition(
        @ModelAttribute CommaSearchConditionRequest commaSearchConditionRequest, Pageable pageable) {
        List<CommaDetailResponse> commaDetailResponses = searchService.searchByUserCondition(
            commaSearchConditionRequest, pageable);
        return new BaseResponse<>(SEARCH_BY_USER_LOG_SUCCESS, commaDetailResponses);
    }

    @GetMapping(path = "/api/commas", params = "type=userdate")
    public BaseResponse<List<CommaDetailResponse>> searchByUserDateCondition(
        @ModelAttribute CommaSearchConditionRequest commaSearchConditionRequest, Pageable pageable) {
        List<CommaDetailResponse> commaDetailResponses = searchService.searchByUserDateCondition(
            commaSearchConditionRequest, pageable);
        return new BaseResponse<>(SEARCH_BY_USER_DATE_LOG_SUCCESS, commaDetailResponses);
    }
}

