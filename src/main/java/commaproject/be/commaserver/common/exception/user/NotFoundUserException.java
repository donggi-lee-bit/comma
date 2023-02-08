package commaproject.be.commaserver.common.exception.user;

import static commaproject.be.commaserver.common.exception.ErrorCodeAndMessage.NOT_FOUND_USER_ERROR;

import commaproject.be.commaserver.common.exception.BaseException;

public class NotFoundUserException extends BaseException {

    public NotFoundUserException() {
        super(NOT_FOUND_USER_ERROR);
    }
}
