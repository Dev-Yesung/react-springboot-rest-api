package kr.co.prgrms.clone.membership.application.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreditPurchaseResponse {
    private final String email;
    private final Integer amount;

    public CreditPurchaseResponse() {
        this.email = null;
        this.amount = null;
    }
}
