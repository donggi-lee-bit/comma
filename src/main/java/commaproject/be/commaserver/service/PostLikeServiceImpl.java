package commaproject.be.commaserver.service;

import commaproject.be.commaserver.common.exception.comma.NotFoundCommaException;
import commaproject.be.commaserver.common.exception.postlike.AlreadyPostLikeException;
import commaproject.be.commaserver.common.exception.postlike.AlreadyPostUnlikeException;
import commaproject.be.commaserver.common.exception.postlike.NotFoundLikeException;
import commaproject.be.commaserver.common.exception.user.NotFoundUserException;
import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.domain.like.Like;
import commaproject.be.commaserver.domain.user.User;
import commaproject.be.commaserver.repository.CommaRepository;
import commaproject.be.commaserver.repository.PostLikeRepository;
import commaproject.be.commaserver.repository.UserRepository;
import commaproject.be.commaserver.service.dto.PostLikeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostLikeServiceImpl implements PostLikeService {

    private final CommaRepository commaRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;

    @Override
    @Transactional
    public void like(PostLikeRequest postLikeRequest, Long loginUserId, Long commaId) {
        User user = userRepository.findById(loginUserId)
            .orElseThrow(NotFoundUserException::new);

        Comma comma = commaRepository.findById(commaId)
            .orElseThrow(NotFoundCommaException::new);

        if (postLikeRepository.findByIdAndCommaId(loginUserId, commaId).isEmpty()) { // 1. Like 를 findById 해왔는데 값이 비어있으면
            postLikeRepository.save(Like.from(postLikeRequest.isPostLikeStatus(),    // 2. Like entity 새로 저장
                loginUserId,
                commaId)
            );
        }

        Like like = postLikeRepository.findByIdAndCommaId(loginUserId, commaId)      // 3. Like entity 가 존재한다면
            .orElseThrow(NotFoundLikeException::new);

        if (like.isLikeStatus()) {                                                   // 4. 해당 Like entity 에서 likeStatus 확인
            throw new AlreadyPostLikeException();                                    // 5. likeStatus 가 true 면 이미 해당 유저는 해당 게시글을 좋아요 했다는 예외 발생
        }

        like.clickPostLike(postLikeRequest.isPostLikeStatus());                      // 6. likeStatus 가 false 면 true 로 상태를 변경해준다
    }

    @Override
    @Transactional
    public void unlike(PostLikeRequest postLikeRequest, Long loginUserId, Long commaId) {
        User user = userRepository.findById(loginUserId)
            .orElseThrow(NotFoundUserException::new);

        Comma comma = commaRepository.findById(commaId)
            .orElseThrow(NotFoundCommaException::new);

        if (!user.isDuplicatePostLike(comma.getId()) || !comma.isDuplicatePostLike(user.getId())) {
            throw new AlreadyPostUnlikeException();
        }

        user.unlike(comma.getId());
        comma.unlike(user.getId());
    }
}
