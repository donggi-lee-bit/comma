package commaproject.be.commaserver.common.exception.user;

import static commaproject.be.commaserver.common.exception.ErrorCodeAndMessage.UN_AUTHORIZED_USER_ERROR;

import commaproject.be.commaserver.common.exception.BaseException;

public class UnAuthorizedUserException extends BaseException {

    public UnAuthorizedUserException() {
        super(UN_AUTHORIZED_USER_ERROR);
    }
}
