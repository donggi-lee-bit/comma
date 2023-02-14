package commaproject.be.commaserver.service;

import commaproject.be.commaserver.common.exception.postlike.AlreadyPostLikeException;
import commaproject.be.commaserver.common.exception.comma.NotFoundCommaException;
import commaproject.be.commaserver.common.exception.postlike.AlreadyPostUnlikeException;
import commaproject.be.commaserver.common.exception.user.NotFoundUserException;
import commaproject.be.commaserver.domain.comma.Comma;
import commaproject.be.commaserver.domain.user.User;
import commaproject.be.commaserver.repository.CommaRepository;
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

    @Transactional
    public void like(PostLikeRequest postLikeRequest, Long loginUserId, Long commaId) {
        User user = userRepository.findById(loginUserId)
            .orElseThrow(NotFoundUserException::new);

        Comma comma = commaRepository.findById(commaId)
            .orElseThrow(NotFoundCommaException::new);

        if (user.isDuplicatePostLike(comma.getId()) || comma.isDuplicatePostLike(user.getId())) {
            throw new AlreadyPostLikeException();
        }

        user.add(comma.getId());
        comma.add(user.getId());
    }

    @Override
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