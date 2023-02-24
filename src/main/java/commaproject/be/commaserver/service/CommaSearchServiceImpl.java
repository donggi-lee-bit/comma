package commaproject.be.commaserver.service;

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
@Transactional(readOnly = true)
public class CommaSearchServiceImpl implements CommaSearchService {

    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final CommaRepository commaRepository;

    public List<CommaDetailResponse> searchByDateCondition(CommaSearchConditionRequest commaSearchConditionRequest) {
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

    public List<CommaDetailResponse> searchByUserCondition(CommaSearchConditionRequest commaSearchConditionRequest) {
        List<Comma> commas = commaRepository.searchByUserCondition(commaSearchConditionRequest.getUsername());

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

    @Override
    public List<CommaDetailResponse> searchByUserDateCondition(CommaSearchConditionRequest commaSearchConditionRequest) {
        LocalDateTime startDate = commaSearchConditionRequest.getDate();
        LocalDateTime endDate = startDate.plusDays(1);
        List<Comma> commas = commaRepository.searchByUserDateCondition(commaSearchConditionRequest.getUsername(), startDate, endDate);

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
        List<Comment> comments = commentRepository.findAllByCommaId(commaId);

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
