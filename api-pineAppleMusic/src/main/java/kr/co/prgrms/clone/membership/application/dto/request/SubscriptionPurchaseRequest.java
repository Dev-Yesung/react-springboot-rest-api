package kr.co.prgrms.clone.membership.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import kr.co.prgrms.clone.membership.domain.PurchaseMonth;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SubscriptionPurchaseRequest {
    @NotBlank(message = "공백이나 스페이스는 이메일에 포함될 수 없습니다.")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    private final String email;
    private final PurchaseMonth requestMonth;

    public SubscriptionPurchaseRequest() {
        this.email = null;
        requestMonth = null;
    }
}
