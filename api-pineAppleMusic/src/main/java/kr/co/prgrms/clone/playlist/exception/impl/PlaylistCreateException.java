package kr.co.prgrms.clone.playlist.exception.impl;

import kr.co.prgrms.clone.global.error.ErrorCode;
import kr.co.prgrms.clone.playlist.exception.PlaylistException;

public class PlaylistCreateException extends PlaylistException {
    public PlaylistCreateException(ErrorCode errorCode) {
        super(errorCode);
    }
}
