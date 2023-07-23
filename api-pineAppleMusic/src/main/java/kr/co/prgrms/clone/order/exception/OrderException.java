package kr.co.prgrms.clone.order.exception;

import kr.co.prgrms.clone.global.error.ErrorCode;

public abstract class OrderException extends RuntimeException {
    private final ErrorCode errorCode;

    public OrderException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
