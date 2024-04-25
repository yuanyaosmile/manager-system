package org.example.yy.exception;

public class AccessException extends RuntimeException{

    private final String errorCode;

    public AccessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public AccessException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode.getCode();
    }

    public String getErrorCode() {
        return errorCode;
    }
}
