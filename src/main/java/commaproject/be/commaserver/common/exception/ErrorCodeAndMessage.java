package commaproject.be.commaserver.common.exception;

import commaproject.be.commaserver.common.response.CodeAndMessage;
import lombok.Getter;

@Getter
public enum ErrorCodeAndMessage implements CodeAndMessage {

    /**
     * 404 Not Found
     */
    NOT_FOUND_USER_ERROR("E-NF001", "해당 유저를 찾을 수 없습니다."),
    NOT_FOUND_COMMA_ERROR("E-NF002", "해당 게시글을 찾을 수 없습니다."),

    /**
     * 400 Bad Request
     */
    UN_AUTHORIZED_USER_ERROR("E-BR001", "해당 행위가 허용되지 않은 유저입니다."),
    ;

    private final String code;
    private final String message;

    ErrorCodeAndMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
