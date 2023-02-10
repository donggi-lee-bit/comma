package commaproject.be.commaserver.common.exception;

import lombok.Getter;

@Getter
public class BaseException extends RuntimeException{

    private final String code;

    public BaseException(ErrorCodeAndMessage errorCodeAndMessage) {
        super(errorCodeAndMessage.getMessage());
        this.code = errorCodeAndMessage.getCode();
    }
}
