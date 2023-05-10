package commaproject.be.commaserver.service;

import commaproject.be.commaserver.common.exception.PageSizeOutOfBoundsException;
import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.domain.comment.Comment;
import commaproject.be.commaserver.repository.CommaRepository;
import commaproject.be.commaserver.repository.CommentRepository;
import commaproject.be.commaserver.repository.PostLikeRepository;
import commaproject.be.commaserver.service.dto.CommaDetailResponse;
import commaproject.be.commaserver.service.dto.CommaPaginatedResponse;
import commaproject.be.commaserver.service.dto.CommaSearchConditionRequest;
import commaproject.be.commaserver.service.dto.CommentDetailResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommaSearchServiceImpl implements CommaSearchService {

    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final CommaRepository commaRepository;

    @Override
    public CommaPaginatedResponse searchByCondition(CommaSearchConditionRequest commaSearchConditionRequest, Pageable pageable) {
        Pageable pageRequest = exchangePageRequest(pageable);

        Page<Comma> commas = commaRepository.searchByCondition(pageRequest, commaSearchConditionRequest.getDate(), commaSearchConditionRequest.getUsername());
        int totalPages = commas.getTotalPages() - 1;

        return new CommaPaginatedResponse(
            pageable.getPageNumber(),
            commas.getContent().size(),
            totalPages,
            getCommaDetailResponses(commas)
        );
    }

    private List<CommaDetailResponse> getCommaDetailResponses(Page<Comma> commas) {
        return commas.stream()
            .map(comma -> new CommaDetailResponse(
                comma.getId(),
                comma.getTitle(),
                comma.getContent(),
                comma.getUsername(),
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
        List<Comment> comments = commentRepository.findAllByCommaId(commaId, commentPageRequest());

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

    private PageRequest commentPageRequest() {
        return PageRequest.of(0, 10);
    }

    private Pageable exchangePageRequest(Pageable pageable) {
        int maxCommaSize = 100;
        int pageSize = pageable.getPageSize();

        if (pageSize <= maxCommaSize) {
            return pageable;
        }

        return PageRequest.of(pageable.getPageNumber(), maxCommaSize);
    }
}
