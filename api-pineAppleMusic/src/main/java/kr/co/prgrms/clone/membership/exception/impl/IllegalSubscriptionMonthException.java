package kr.co.prgrms.clone.membership.exception.impl;

import kr.co.prgrms.clone.global.error.ErrorCode;
import kr.co.prgrms.clone.membership.exception.MembershipException;

public class IllegalSubscriptionMonthException extends MembershipException {
    public IllegalSubscriptionMonthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
