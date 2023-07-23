package kr.co.prgrms.clone.membership.domain.impl;

import kr.co.prgrms.clone.global.error.ErrorCode;
import kr.co.prgrms.clone.membership.domain.Membership;
import kr.co.prgrms.clone.membership.domain.PurchaseAmount;
import kr.co.prgrms.clone.membership.exception.impl.IllegalCreditAmountException;
import lombok.Getter;

import static kr.co.prgrms.clone.global.error.ErrorCode.*;

@Getter
public class Credit extends Membership {
    private Integer amount;

    public Credit(Integer id, Integer userId, Integer amount) {
        super(id, userId);
        this.amount = amount;
    }

    public Credit(Integer userId) {
        super(null, userId);
        this.amount = 0;
    }

    public void addPurchasedAmount(Integer amount) {
        if (!PurchaseAmount.isValidAmount(amount)) {
            throw new IllegalCreditAmountException(ILLEGAL_CREDIT_AMOUNT_PURCHASE);
        }
        this.amount += amount;
    }

    public Integer subtractAmount(Integer amount) {
        if (this.amount - amount < 0) {
            throw new IllegalCreditAmountException(SHORT_OF_TOTAL_CREDITS);
        }
        this.amount -= amount;

        return this.amount;
    }
}
