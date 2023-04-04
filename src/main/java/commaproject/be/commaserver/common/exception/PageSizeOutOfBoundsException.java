package commaproject.be.commaserver.common.exception;

public class PageSizeOutOfBoundsException extends
    BaseException {

    public PageSizeOutOfBoundsException() {
        super(ErrorCodeAndMessage.PAGE_SIZE_OUT_OF_BOUNDS);
    }
}
