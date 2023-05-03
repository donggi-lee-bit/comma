package commaproject.be.commaserver.controller;

import static commaproject.be.commaserver.common.response.ResponseCodeAndMessage.CREATE_COMMA_LOG_SUCCESS;
import static commaproject.be.commaserver.common.response.ResponseCodeAndMessage.DELETE_COMMA_LOG_SUCCESS;
import static commaproject.be.commaserver.common.response.ResponseCodeAndMessage.READ_ALL_COMMA_LOG_SUCCESS;
import static commaproject.be.commaserver.common.response.ResponseCodeAndMessage.READ_COMMA_LOG_SUCCESS;
import static commaproject.be.commaserver.common.response.ResponseCodeAndMessage.UPDATE_COMMA_LOG_SUCCESS;

import commaproject.be.commaserver.common.response.BaseResponse;
import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.domain.user.AuthenticatedUser;
import commaproject.be.commaserver.service.CommaService;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommaPaginatedResponse;
import commaproject.be.commaserver.service.dto.CommaRequest;
import commaproject.be.commaserver.service.dto.CommaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
        return new BaseResponse<>(READ_COMMA_LOG_SUCCESS, commaDetailResponse);
    }

    @GetMapping("/api/commas")
    public BaseResponse<CommaPaginatedResponse> readAll(Pageable pageable) {
        CommaPaginatedResponse commaPaginatedResponse = commaService.readAll(pageable);
        return new BaseResponse<>(READ_ALL_COMMA_LOG_SUCCESS, commaPaginatedResponse);
    }

    @PostMapping("/api/commas")
    public BaseResponse<CommaResponse> create(
        @AuthenticatedUser Long loginUserId,
        @RequestBody CommaRequest commaRequest) {
        CommaResponse commaResponse = commaService.create(loginUserId, commaRequest);
        return new BaseResponse<>(CREATE_COMMA_LOG_SUCCESS, commaResponse);
    }

    @PutMapping("/api/commas/{commaId}")
    public BaseResponse<CommaDetailResponse> update(
        @PathVariable Long commaId,
        @AuthenticatedUser Long loginUserId,
        @RequestBody CommaRequest commaRequest) {
        CommaDetailResponse updateCommaDetailResponse = commaService.update(loginUserId, commaId, commaRequest);
        return new BaseResponse<>(UPDATE_COMMA_LOG_SUCCESS, updateCommaDetailResponse);
    }

    @DeleteMapping("/api/commas/{commaId}")
    public BaseResponse<CommaResponse> delete(@PathVariable Long commaId, @AuthenticatedUser Long loginUserId) {
        Comma comma = commaService.remove(loginUserId, commaId);
        return new BaseResponse<>(DELETE_COMMA_LOG_SUCCESS, new CommaResponse(comma.getId()));
    }
}
