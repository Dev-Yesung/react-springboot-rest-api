package kr.co.prgrms.clone.order.exception.impl;

import kr.co.prgrms.clone.global.error.ErrorCode;
import kr.co.prgrms.clone.order.exception.OrderException;

public class OrderNotFoundException extends OrderException {
    public OrderNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
