package commaproject.be.commaserver.common.exception.postlike;

import static commaproject.be.commaserver.common.exception.ErrorCodeAndMessage.NOT_FOUND_LIKE_ERROR;

import commaproject.be.commaserver.common.exception.BaseException;

public class NotFoundLikeException extends BaseException {

    public NotFoundLikeException() {
        super(NOT_FOUND_LIKE_ERROR);
    }
}
