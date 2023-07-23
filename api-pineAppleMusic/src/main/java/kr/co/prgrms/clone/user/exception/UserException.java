package kr.co.prgrms.clone.user.exception;

import kr.co.prgrms.clone.global.error.ErrorCode;

public abstract class UserException extends RuntimeException {
    private final ErrorCode errorCode;

    public UserException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
