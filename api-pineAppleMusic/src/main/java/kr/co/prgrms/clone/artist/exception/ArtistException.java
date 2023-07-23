package kr.co.prgrms.clone.artist.exception;

import kr.co.prgrms.clone.global.error.ErrorCode;

public abstract class ArtistException extends RuntimeException {
    private final ErrorCode errorCode;

    public ArtistException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
