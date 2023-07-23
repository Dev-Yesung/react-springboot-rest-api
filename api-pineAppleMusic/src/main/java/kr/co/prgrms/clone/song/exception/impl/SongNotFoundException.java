package kr.co.prgrms.clone.song.exception.impl;

import kr.co.prgrms.clone.global.error.ErrorCode;
import kr.co.prgrms.clone.song.exception.SongException;

public class SongNotFoundException extends SongException {
    public SongNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
