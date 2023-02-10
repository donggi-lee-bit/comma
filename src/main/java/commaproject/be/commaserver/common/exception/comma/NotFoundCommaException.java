package commaproject.be.commaserver.common.exception.comma;

import static commaproject.be.commaserver.common.exception.ErrorCodeAndMessage.NOT_FOUND_COMMA_ERROR;

import commaproject.be.commaserver.common.exception.BaseException;

public class NotFoundCommaException extends BaseException {

    public NotFoundCommaException() {
        super(NOT_FOUND_COMMA_ERROR);
    }
}
