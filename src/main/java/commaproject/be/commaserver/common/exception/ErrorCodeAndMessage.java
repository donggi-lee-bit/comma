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
    NOT_FOUND_COMMENT_ERROR("E-NF003", "해당 댓글을 찾을 수 없습니다."),
    NOT_FOUND_LIKE_ERROR("E-NF004", "해당 좋아요 이력을 찾을 수 없습니다."),

    /**
     * 400 Bad Request
     */
    UN_AUTHORIZED_USER_ERROR("E-BR001", "해당 행위가 허용되지 않은 유저입니다."),
    ALREADY_POST_LIKE_ERROR("E-BR002", "이미 좋아요 한 게시글입니다."),
    ALREADY_POST_UNLIKE_ERROR("E-BR003", "좋아요 하지 않은 게시글입니다."),
    PAGE_SIZE_OUT_OF_BOUNDS("E-BR004", "요청한 페이지 크기가 너무 큽니다.")
    ;

    private final String code;
    private final String message;

    ErrorCodeAndMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
