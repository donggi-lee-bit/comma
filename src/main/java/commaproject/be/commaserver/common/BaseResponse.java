package commaproject.be.commaserver.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BaseResponse<T> {

    private String code;
    private String message;
    private T data;

}
