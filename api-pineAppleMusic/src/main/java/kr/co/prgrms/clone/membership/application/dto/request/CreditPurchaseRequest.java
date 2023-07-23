package kr.co.prgrms.clone.membership.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import kr.co.prgrms.clone.membership.domain.PurchaseAmount;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreditPurchaseRequest {
    @NotBlank(message = "공백이나 스페이스는 이메일에 포함될 수 없습니다.")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    private final String email;
    private final PurchaseAmount requestAmount;

    public CreditPurchaseRequest() {
        this.email = null;
        this.requestAmount = null;
    }
}
