package kr.co.prgrms.clone.artist.exception.impl;

import kr.co.prgrms.clone.artist.exception.ArtistException;
import kr.co.prgrms.clone.global.error.ErrorCode;

public class ArtistNotFoundException extends ArtistException {
    public ArtistNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
