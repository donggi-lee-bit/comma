package commaproject.be.commaserver.common.exception.postlike;

import static commaproject.be.commaserver.common.exception.ErrorCodeAndMessage.ALREADY_POST_UNLIKE_ERROR;

import commaproject.be.commaserver.common.exception.BaseException;

public class AlreadyPostUnlikeException extends BaseException {

    public AlreadyPostUnlikeException() {
        super(ALREADY_POST_UNLIKE_ERROR);
    }
}
