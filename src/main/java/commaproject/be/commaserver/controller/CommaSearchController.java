package commaproject.be.commaserver.controller;


import static commaproject.be.commaserver.common.response.ResponseCodeAndMessage.SEARCH_BY_DATE_LOG_SUCCESS;
import static commaproject.be.commaserver.common.response.ResponseCodeAndMessage.SEARCH_BY_USER_DATE_LOG_SUCCESS;
import static commaproject.be.commaserver.common.response.ResponseCodeAndMessage.SEARCH_BY_USER_LOG_SUCCESS;

import commaproject.be.commaserver.common.response.BaseResponse;
import commaproject.be.commaserver.domain.comma.ValidPage;
import commaproject.be.commaserver.service.CommaSearchService;
import commaproject.be.commaserver.service.dto.CommaPaginatedResponse;
import commaproject.be.commaserver.service.dto.CommaSearchConditionRequest;
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
    public BaseResponse<CommaPaginatedResponse> searchByDateCondition(
        @ModelAttribute CommaSearchConditionRequest commaSearchConditionRequest, @ValidPage Pageable pageable) {
        CommaPaginatedResponse commaPaginatedResponse = searchService.searchByCondition(commaSearchConditionRequest, pageable);
        return new BaseResponse<>(SEARCH_BY_DATE_LOG_SUCCESS, commaPaginatedResponse);
    }

    @GetMapping(path = "/api/commas", params = "type=user")
    public BaseResponse<CommaPaginatedResponse> searchByUserCondition(
        @ModelAttribute CommaSearchConditionRequest commaSearchConditionRequest, @ValidPage Pageable pageable) {
        CommaPaginatedResponse commaPaginatedResponse = searchService.searchByCondition(commaSearchConditionRequest, pageable);
        return new BaseResponse<>(SEARCH_BY_USER_LOG_SUCCESS, commaPaginatedResponse);
    }

    @GetMapping(path = "/api/commas", params = "type=userdate")
    public BaseResponse<CommaPaginatedResponse> searchByUserDateCondition(
        @ModelAttribute CommaSearchConditionRequest commaSearchConditionRequest, @ValidPage Pageable pageable) {
        CommaPaginatedResponse commaPaginatedResponse = searchService.searchByCondition(commaSearchConditionRequest, pageable);
        return new BaseResponse<>(SEARCH_BY_USER_DATE_LOG_SUCCESS, commaPaginatedResponse);
    }
}

