package kr.co.prgrms.clone.artist.exception.impl;

import kr.co.prgrms.clone.artist.exception.ArtistException;
import kr.co.prgrms.clone.global.error.ErrorCode;

public class ArtistBlobReadException extends ArtistException {
    public ArtistBlobReadException(ErrorCode errorCode) {
        super(errorCode);
    }
}
