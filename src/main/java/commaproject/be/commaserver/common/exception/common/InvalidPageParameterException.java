package commaproject.be.commaserver.common.exception.common;

import static commaproject.be.commaserver.common.exception.ErrorCodeAndMessage.INVALID_PAGE_PARAMETER_ERROR;

import commaproject.be.commaserver.common.exception.BaseException;

public class InvalidPageParameterException extends BaseException {

    public InvalidPageParameterException() {
        super(INVALID_PAGE_PARAMETER_ERROR);
    }
}
