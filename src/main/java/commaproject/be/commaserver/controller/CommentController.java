package commaproject.be.commaserver.controller;

import commaproject.be.commaserver.common.BaseResponse;
import commaproject.be.commaserver.service.CommentService;
import commaproject.be.commaserver.service.dto.CommentRequest;
import commaproject.be.commaserver.service.dto.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/commas/{commaId}")
    public BaseResponse<CommentResponse> create(@PathVariable Long commaId, @RequestBody CommentRequest commentRequest) {
        CommentResponse commentResponse = commentService.create(commaId, commentRequest);
        return new BaseResponse<>("200", "OK", commentResponse);
    }
}
