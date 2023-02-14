package commaproject.be.commaserver.common.exception.postlike;

import commaproject.be.commaserver.common.exception.BaseException;
import commaproject.be.commaserver.common.exception.ErrorCodeAndMessage;

public class AlreadyPostUnlikeException extends BaseException {

    public AlreadyPostUnlikeException() {
        super(ErrorCodeAndMessage.ALREADY_POST_UNLIKE_ERROR);
    }
}
