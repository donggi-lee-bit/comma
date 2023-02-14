package commaproject.be.commaserver.common.response;

import lombok.Getter;

@Getter
public enum ResponseCodeAndMessage implements CodeAndMessage {

    /**
     * Comma
     */
    READ_ALL_COMMA_LOG_SUCCESS("C-CA001", "모든 게시글 조회를 성공했습니다."),
    READ_COMMA_LOG_SUCCESS("C-CA002", "특정 게시글 조회를 성공했습니다."),
    CREATE_COMMA_LOG_SUCCESS("C-CA003", "게시글 작성을 성공했습니다."),
    UPDATE_COMMA_LOG_SUCCESS("C-CA004", "게시글 수정을 성공했습니다."),
    DELETE_COMMA_LOG_SUCCESS("C-CA005", "게시글 삭제를 성공했습니다."),

    /**
     * Comma Search
     */
    SEARCH_BY_DATE_LOG_SUCCESS("C-SC001", "특정 날짜에 작성한 게시글 조회를 성공했습니다."),
    SEARCH_BY_USER_LOG_SUCCESS("C-SC002", "특정 유저가 작성한 게시글 조회를 성공했습니다."),
    SEARCH_BY_USER_DATE_LOG_SUCCESS("C-SC003", "특정 날짜에 특정 유저가 작성한 게시글 조회를 성공했습니다."),

    /**
     * Comment
     */
    CREATE_COMMENT_LOG_SUCCESS("C-CE001", "댓글 작성을 성공했습니다."),
    UPDATE_COMMENT_LOG_SUCCESS("C-CE002", "댓글 수정을 성공했습니다."),
    DELETE_COMMENT_LOG_SUCCESS("C-CE003", "댓글 삭제를 성공했습니다."),
    READ_ALL_COMMENT_LOG_SUCCESS("C-CE004", "해당 게시글의 모든 댓글 조회를 성공했습니다."),
    READ_ONE_COMMENT_LOG_SUCCESS("C-CE005", "해당 게시글의 댓글 조회를 성공했습니다"),

    /**
     * Login
     */
    OAUTH_LOGIN_SUCCESS("C-LO001", "OAUTH 로그인을 성공했습니다."),


    ;

    private final String code;
    private final String message;

    ResponseCodeAndMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
