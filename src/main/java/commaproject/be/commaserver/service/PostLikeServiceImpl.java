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
    public void like(Long loginUserId, Long commaId) {
        User user = userRepository.findById(loginUserId)
            .orElseThrow(NotFoundUserException::new);

        Comma comma = commaRepository.findById(commaId)
            .orElseThrow(NotFoundCommaException::new);

        Like like = findLike(user.getId(), comma.getId());

        if (like.isLikeStatus()) {                                                   // 4. 해당 Like entity 에서 likeStatus 확인
            throw new AlreadyPostLikeException();                                    // 5. likeStatus 가 true 면 이미 해당 유저는 해당 게시글을 좋아요 했다는 예외 발생
        }

        like.clickPostLike(true);                      // 6. likeStatus 가 false 면 true 로 상태를 변경해준다
    }

    @Override
    @Transactional
    public void unlike(Long loginUserId, Long commaId) {
        User user = userRepository.findById(loginUserId)
            .orElseThrow(NotFoundUserException::new);

        Comma comma = commaRepository.findById(commaId)
            .orElseThrow(NotFoundCommaException::new);

        Like like = findLike(user.getId(), comma.getId());

        if (!like.isLikeStatus()) {
            throw new AlreadyPostUnlikeException();
        }

        like.clickPostLike(false);
    }

    private Like findLike(Long loginUserId, Long commaId) {
        if (postLikeRepository.findByIdAndCommaId(loginUserId, commaId).isEmpty()) {
            return postLikeRepository.save(Like.of(loginUserId, commaId));
        }

        return postLikeRepository.findByIdAndCommaId(loginUserId, commaId)
            .orElseThrow(NotFoundLikeException::new);
    }
}
