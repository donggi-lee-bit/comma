package commaproject.be.commaserver.common.exception.comment;

import static commaproject.be.commaserver.common.exception.ErrorCodeAndMessage.NOT_FOUND_COMMENT_ERROR;

import commaproject.be.commaserver.common.exception.BaseException;

public class NotFoundCommentException extends BaseException {

    public NotFoundCommentException() {
        super(NOT_FOUND_COMMENT_ERROR);
    }
}
