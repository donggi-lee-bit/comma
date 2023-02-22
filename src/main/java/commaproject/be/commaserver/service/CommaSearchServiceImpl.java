package commaproject.be.commaserver.service;

import commaproject.be.commaserver.common.exception.comment.NotFoundCommentException;
import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.domain.comment.Comment;
import commaproject.be.commaserver.repository.CommaRepository;
import commaproject.be.commaserver.repository.CommentRepository;
import commaproject.be.commaserver.repository.PostLikeRepository;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommaSearchConditionRequest;
import commaproject.be.commaserver.service.dto.CommentDetailResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommaSearchServiceImpl implements CommaSearchService {

    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final CommaRepository commaRepository;

    @Transactional
    public List<CommaDetailResponse> searchByCondition(CommaSearchConditionRequest commaSearchConditionRequest) {
        LocalDateTime startDate = commaSearchConditionRequest.getDate();
        LocalDateTime endDate = startDate.plusDays(1);

        List<Comma> commas = commaRepository.searchByDateCondition(startDate, endDate);

        return commas.stream()
            .map(comma -> new CommaDetailResponse(
                comma.getId(),
                comma.getTitle(),
                comma.getContent(),
                comma.getUser().getUsername(),
                comma.getUser().getId(),
                comma.getCreatedAt(),
                getPostLikeCount(comma.getId()),
                getCommentDetailResponses(comma.getId())))
            .collect(Collectors.toUnmodifiableList());
    }

    private Long getPostLikeCount(Long commaId) {
        return postLikeRepository.countLikeByCommaIdAndLikeStatus(commaId, true);
    }

    private List<CommentDetailResponse> getCommentDetailResponses(Long commaId) {
        List<Comment> comments = commentRepository.findAllByCommaId(commaId)
            .orElseThrow(NotFoundCommentException::new);

        return comments.stream()
            .map(comment -> new CommentDetailResponse(
                comment.getId(),
                comment.getUserId(),
                comment.getUsername(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()))
            .collect(Collectors.toUnmodifiableList());
    }
}
