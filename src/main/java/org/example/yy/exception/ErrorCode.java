package org.example.yy.exception;

public enum ErrorCode {
    RESOURCE_NOT_FOUND("100001","Resource not found"),
    USER_NOT_FOUND("100002", "User not found"),
    ROLE_INFO_MISSING("100003", "role info missing"),

    REMOTE_SERVER_ERROR("100004", "Remote server error"),
    ROLE_INFO_NOT_CORRECT("100005", "Role info not correct"),
    ACCESS_DENIED("100006", "Access denied")
    ;

    private final String code;
    private final String description;

    ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
