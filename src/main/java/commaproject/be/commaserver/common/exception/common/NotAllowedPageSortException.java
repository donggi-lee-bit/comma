package commaproject.be.commaserver.common.exception.common;

import static commaproject.be.commaserver.common.exception.ErrorCodeAndMessage.NOT_ALLOWED_PAGE_SORT_ERROR;

import commaproject.be.commaserver.common.exception.BaseException;

public class NotAllowedPageSortException extends BaseException {

    public NotAllowedPageSortException() {
        super(NOT_ALLOWED_PAGE_SORT_ERROR);
    }
}
