package commaproject.be.commaserver.service;

import commaproject.be.commaserver.common.exception.comma.NotFoundCommaException;
import commaproject.be.commaserver.common.exception.postlike.AlreadyPostLikeException;
import commaproject.be.commaserver.common.exception.postlike.AlreadyPostUnlikeException;
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

        Like like = postLikeRepository.findByIdAndCommaId(user.getId(), comma.getId())
            .orElseGet(() -> postLikeRepository.save(Like.of(user, comma)));

        if (like.isLikeStatus()) {
            throw new AlreadyPostLikeException();
        }

        like.update(true);
    }

    @Override
    @Transactional
    public void unlike(Long loginUserId, Long commaId) {
        User user = userRepository.findById(loginUserId)
            .orElseThrow(NotFoundUserException::new);

        Comma comma = commaRepository.findById(commaId)
            .orElseThrow(NotFoundCommaException::new);

        Like like = postLikeRepository.findByIdAndCommaId(user.getId(), comma.getId())
            .orElseGet(() -> postLikeRepository.save(Like.of(user, comma)));

        if (!like.isLikeStatus()) {
            throw new AlreadyPostUnlikeException();
        }

        like.update(false);
    }
}
