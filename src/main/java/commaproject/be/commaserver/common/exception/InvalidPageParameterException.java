package commaproject.be.commaserver.common.exception;

import static commaproject.be.commaserver.common.exception.ErrorCodeAndMessage.INVALID_PAGE_PARAMETER_ERROR;

public class InvalidPageParameterException extends BaseException {

    public InvalidPageParameterException() {
        super(INVALID_PAGE_PARAMETER_ERROR);
    }
}
