package commaproject.be.commaserver.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class BaseResponse<T> {

    private final String code;
    private final String message;
    private final T data;

    public BaseResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(CodeAndMessage codeAndMessage, T data) {
        this.code = codeAndMessage.getCode();
        this.message = codeAndMessage.getMessage();
        this.data = data;
    }
}
