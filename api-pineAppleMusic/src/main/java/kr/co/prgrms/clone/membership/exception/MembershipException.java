package kr.co.prgrms.clone.membership.exception;

import kr.co.prgrms.clone.global.error.ErrorCode;

public abstract class MembershipException extends RuntimeException {
    private final ErrorCode errorCode;

    public MembershipException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
