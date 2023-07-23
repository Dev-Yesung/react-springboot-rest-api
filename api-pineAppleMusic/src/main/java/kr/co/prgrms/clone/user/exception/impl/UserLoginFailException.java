package kr.co.prgrms.clone.user.exception.impl;

import kr.co.prgrms.clone.global.error.ErrorCode;
import kr.co.prgrms.clone.user.exception.UserException;

public class UserLoginFailException extends UserException {
    public UserLoginFailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
