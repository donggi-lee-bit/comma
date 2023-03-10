package commaproject.be.commaserver.controller;

import static commaproject.be.commaserver.common.response.ResponseCodeAndMessage.LIKE_POST_LOG_SUCCESS;

import commaproject.be.commaserver.common.response.BaseResponse;
import commaproject.be.commaserver.common.response.ResponseCodeAndMessage;
import commaproject.be.commaserver.domain.user.AuthenticatedUser;
import commaproject.be.commaserver.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/api/likes/{commaId}/like")
    public BaseResponse<Void> clickPostLike(
        @PathVariable Long commaId,
        @AuthenticatedUser Long loginUserId) {
        postLikeService.like(loginUserId, commaId);
        return new BaseResponse<>(LIKE_POST_LOG_SUCCESS, null);
    }

    @PostMapping("/api/likes/{commaId}/unlike")
    public BaseResponse<Void> clickPostUnlike(
        @PathVariable Long commaId,
        @AuthenticatedUser Long loginUserId) {
        postLikeService.unlike(loginUserId, commaId);
        return new BaseResponse<>(ResponseCodeAndMessage.UNLIKE_POST_LOG_SUCCESS, null);
    }
}
