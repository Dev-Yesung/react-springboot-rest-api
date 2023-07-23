package kr.co.prgrms.clone.membership.domain.impl;

import kr.co.prgrms.clone.global.error.ErrorCode;
import kr.co.prgrms.clone.membership.domain.PurchaseMonth;
import kr.co.prgrms.clone.membership.domain.Membership;
import kr.co.prgrms.clone.membership.exception.impl.IllegalSubscriptionMonthException;
import kr.co.prgrms.clone.membership.exception.impl.MembershipPurchaseFailException;
import lombok.Getter;

import java.time.LocalDateTime;

import static kr.co.prgrms.clone.global.error.ErrorCode.*;

@Getter
public class Subscription extends Membership {
    private LocalDateTime expiredAt;

    public Subscription(Integer id, Integer userId, LocalDateTime expiredAt) {
        super(id, userId);
        this.expiredAt = expiredAt;
    }

    public Subscription(Integer userId) {
        super(null, userId);
        this.expiredAt = LocalDateTime.now();
    }

    public void addExpiredAt(Integer month) {
        if (!PurchaseMonth.isValidMonth(month)) {
            throw new IllegalSubscriptionMonthException(ILLEGAL_SUBSCRIPTION_MONTH_PURCHASE);
        }
        expiredAt = expiredAt.plusMonths(month);
    }
}
