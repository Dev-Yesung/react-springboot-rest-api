package kr.co.prgrms.clone.membership.domain;

import java.util.Arrays;

public enum PurchaseAmount {
    FIVE_THOUSAND_WON(5_000),
    TEN_THOUSAND_WON(10_000),
    TWENTY_THOUSAND_WON(20_000),
    FIFTY_THOUSAND_WON(50_000);

    private final Integer amount;

    PurchaseAmount(Integer amount) {
        this.amount = amount;
    }

    public static boolean isValidAmount(Integer amount) {
        return Arrays.stream(values())
                .anyMatch(purchaseAmount -> purchaseAmount.getAmount().equals(amount));
    }

    public Integer getAmount() {
        return amount;
    }
}
