package kr.co.prgrms.clone.playlist.exception.impl;

import kr.co.prgrms.clone.global.error.ErrorCode;
import kr.co.prgrms.clone.playlist.exception.PlaylistException;

public class PlaylistAddException extends PlaylistException {
    public PlaylistAddException(ErrorCode errorCode) {
        super(errorCode);
    }
}
