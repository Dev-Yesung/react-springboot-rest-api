package kr.co.prgrms.clone.user.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@ToString
@EqualsAndHashCode
@Getter
@RequiredArgsConstructor
public final class UserSignUpRequest {

    @Size(min = 2, max = 50, message = "이메일은 50자를 초과할 수 없습니다.")
    @NotBlank(message = "공백이나 스페이스는 이메일에 포함될 수 없습니다.")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    private final String email;

    @NotBlank(message = "공백이나 스페이스는 비밀번호에 포함될 수 없습니다.")
    @Length(min = 5, max = 20, message = "패스워드는 5자리 이상 20자리이하만 가능합니다.")
    private final String password;

    public UserSignUpRequest() {
        this.email = null;
        this.password = null;
    }
}
