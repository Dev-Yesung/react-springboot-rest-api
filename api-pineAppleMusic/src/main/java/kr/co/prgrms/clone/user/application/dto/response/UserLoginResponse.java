package kr.co.prgrms.clone.user.application.dto.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import kr.co.prgrms.clone.membership.domain.MembershipType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
@Getter
@RequiredArgsConstructor
public final class UserLoginResponse {

    @NotBlank(message = "공백이나 스페이스는 이메일에 포함될 수 없습니다.")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    private final String userEmail;
    private final MembershipType membershipType;
    private final LocalDateTime loginAt;

}
