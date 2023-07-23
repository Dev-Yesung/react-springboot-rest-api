package kr.co.prgrms.clone.song.exception;

import kr.co.prgrms.clone.global.error.ErrorCode;

public abstract class SongException extends RuntimeException {
    private final ErrorCode errorCode;

    public SongException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
