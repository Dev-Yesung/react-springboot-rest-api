package kr.co.prgrms.clone.user.domain;

import kr.co.prgrms.clone.user.exception.impl.UserLoginFailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserTest {
    private final String validEmail = "test1234@gmail.com";
    private final String validPassword = "!test1234";

    @Test
    @DisplayName("올바른 이메일과 패스워드를 받으면 생성에 성공")
    void createUserIfValidEmailAndPassword() {
        // when
        User user = createUser(validEmail, validPassword);

        // then
        assertThat(user.getEmail()).isEqualTo(validEmail);
        assertThat(user.getPassword()).isEqualTo(validPassword);
    }

    @ParameterizedTest
    @ValueSource(strings = {"    ", "1234", "gfyueqv", "@#$%^", "][[p][ojohcb"})
    @DisplayName("입력된 비밀번호가 유저의 비밀번호와 일치하지 않을 때 예외 발생")
    void throwExceptionIfInputPasswordIsNotEqualToUserPassword(String inputPassword) {
        // when
        User user = new User(validEmail, validPassword);
        // then
        assertThatThrownBy(() -> user.checkPasswordEquality(inputPassword))
                .isInstanceOf(UserLoginFailException.class);
    }

    private User createUser(String email, String password) {
        return new User(email, password);
    }
}
