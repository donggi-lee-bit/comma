package commaproject.be.commaserver.common.exception.postlike;

import static commaproject.be.commaserver.common.exception.ErrorCodeAndMessage.ALREADY_POST_LIKE_ERROR;

import commaproject.be.commaserver.common.exception.BaseException;

public class AlreadyPostLikeException extends BaseException {

    public AlreadyPostLikeException() {
        super(ALREADY_POST_LIKE_ERROR);
    }
}
