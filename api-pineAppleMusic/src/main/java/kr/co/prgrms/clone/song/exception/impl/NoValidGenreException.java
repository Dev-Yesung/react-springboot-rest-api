package kr.co.prgrms.clone.song.exception.impl;

import kr.co.prgrms.clone.global.error.ErrorCode;
import kr.co.prgrms.clone.song.exception.SongException;

public class NoValidGenreException extends SongException {
    public NoValidGenreException(ErrorCode errorCode) {
        super(errorCode);
    }
}
