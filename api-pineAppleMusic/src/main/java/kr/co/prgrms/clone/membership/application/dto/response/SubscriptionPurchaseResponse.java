package kr.co.prgrms.clone.membership.application.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class SubscriptionPurchaseResponse {
    private final String email;
    private final LocalDateTime expiredAt;

    public SubscriptionPurchaseResponse() {
        this.email = null;
        this.expiredAt = null;
    }
}
