package commaproject.be.commaserver.controller;

import static commaproject.be.commaserver.common.response.ResponseCodeAndMessage.CREATE_COMMENT_LOG_SUCCESS;
import static commaproject.be.commaserver.common.response.ResponseCodeAndMessage.DELETE_COMMENT_LOG_SUCCESS;
import static commaproject.be.commaserver.common.response.ResponseCodeAndMessage.READ_ALL_COMMENT_LOG_SUCCESS;
import static commaproject.be.commaserver.common.response.ResponseCodeAndMessage.READ_ONE_COMMENT_LOG_SUCCESS;
import static commaproject.be.commaserver.common.response.ResponseCodeAndMessage.UPDATE_COMMENT_LOG_SUCCESS;

import commaproject.be.commaserver.common.response.BaseResponse;
import commaproject.be.commaserver.domain.comment.Comment;
import commaproject.be.commaserver.domain.user.AuthenticatedUser;
import commaproject.be.commaserver.service.CommentService;
import commaproject.be.commaserver.service.dto.CommentDetailResponse;
import commaproject.be.commaserver.service.dto.CommentRequest;
import commaproject.be.commaserver.service.dto.CommentResponse;
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
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/api/commas/{commaId}/comments")
    public BaseResponse<List<CommentDetailResponse>> readAll(@PathVariable Long commaId) {
        List<CommentDetailResponse> commentDetailResponses = commentService.readAll(commaId);
        return new BaseResponse<>(READ_ALL_COMMENT_LOG_SUCCESS, commentDetailResponses);
    }

    @GetMapping("/api/commas/{commaId}/comments/{commentId}")
    public BaseResponse<CommentDetailResponse> readOne(@PathVariable Long commaId, @PathVariable Long commentId) {
        CommentDetailResponse commentDetailResponse = commentService.readOne(commaId, commentId);
        return new BaseResponse<>(READ_ONE_COMMENT_LOG_SUCCESS, commentDetailResponse);
    }

    @PostMapping("/api/commas/{commaId}/comments")
    public BaseResponse<CommentResponse> create(
        @AuthenticatedUser Long loginUserId,
        @PathVariable Long commaId,
        @RequestBody CommentRequest commentRequest) {
        CommentResponse commentResponse = commentService.create(loginUserId, commaId, commentRequest);
        return new BaseResponse<>(CREATE_COMMENT_LOG_SUCCESS, commentResponse);
    }

    @PutMapping("/api/commas/{commaId}/comments/{commentId}")
    public BaseResponse<CommentDetailResponse> update(
        @AuthenticatedUser Long loginUserId,
        @PathVariable Long commaId,
        @PathVariable Long commentId,
        @RequestBody CommentRequest commentRequest) {
        CommentDetailResponse commentDetailResponse = commentService.update(loginUserId, commaId, commentId, commentRequest);
        return new BaseResponse<>(UPDATE_COMMENT_LOG_SUCCESS, commentDetailResponse);
    }

    @DeleteMapping("/api/commas/{commaId}/comments/{commentId}")
    public BaseResponse<CommentResponse> delete(
        @AuthenticatedUser Long loginUserId,
        @PathVariable Long commaId,
        @PathVariable Long commentId) {
        Comment comment = commentService.delete(loginUserId, commaId, commentId);
        return new BaseResponse<>(DELETE_COMMENT_LOG_SUCCESS, new CommentResponse(comment.getId()));
    }
}
