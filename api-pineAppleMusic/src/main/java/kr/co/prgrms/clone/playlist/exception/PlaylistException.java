package kr.co.prgrms.clone.playlist.exception;

import kr.co.prgrms.clone.global.error.ErrorCode;

public abstract class PlaylistException extends RuntimeException {
    private final ErrorCode errorCode;

    public PlaylistException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
