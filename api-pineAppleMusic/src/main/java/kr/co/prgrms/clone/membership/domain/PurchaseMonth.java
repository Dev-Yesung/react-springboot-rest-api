package kr.co.prgrms.clone.membership.domain;

import java.util.Arrays;

public enum PurchaseMonth {
    ONE_MONTH(1),
    THREE_MONTH(3),
    SIX_MONTH(6),
    ONE_YEAR(12);

    private final Integer month;

    PurchaseMonth(Integer month) {
        this.month = month;
    }

    public static boolean isValidMonth(Integer month) {
        return Arrays.stream(values())
                .anyMatch(purchaseMonth -> purchaseMonth.getMonth().equals(month));
    }

    public Integer getMonth() {
        return month;
    }
}
