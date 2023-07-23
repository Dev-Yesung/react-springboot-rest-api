package kr.co.prgrms.clone.user.application;

import kr.co.prgrms.clone.user.application.dto.request.UserLoginRequest;
import kr.co.prgrms.clone.user.application.dto.request.UserSignUpRequest;
import kr.co.prgrms.clone.user.application.dto.response.UserLoginResponse;
import kr.co.prgrms.clone.user.application.dto.response.UserSignUpResponse;
import kr.co.prgrms.clone.user.exception.impl.UserLoginFailException;
import kr.co.prgrms.clone.user.exception.impl.UserSignUpFailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceTest {
    private static final String email = "test@gmail.com";
    private static final String password = "!@test1234";

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("회원이 중복될 경우 이메일 체크에서 예외발생")
    public void throwExceptionWhenCheckEmailIfDuplicateUserEmail() {
        // given
        UserSignUpRequest request = new UserSignUpRequest(email, password);
        // when
        userService.join(request);
        // then
        assertThatThrownBy(() -> userService.checkDuplicateEmail(email))
                .isInstanceOf(UserSignUpFailException.class);
    }

    @Test
    @DisplayName("회원이 중복이 아닐 경우 예외 발생X")
    public void passWhenCheckEmailIfNoDuplicateUserEmail() {
        // when, then
        assertThatNoException()
                .isThrownBy(() -> userService.checkDuplicateEmail(email));
    }

    @Test
    @DisplayName("회원이 중복될 경우 가입실패")
    public void joinFailIfDuplicateUserEmail() {
        // given
        UserSignUpRequest request = new UserSignUpRequest(email, password);
        // when
        userService.join(request);
        // then
        assertThatThrownBy(() -> userService.join(request))
                .isInstanceOf(UserSignUpFailException.class);
    }

    @Test
    @DisplayName("중복회원이 없을 경우 가입성공")
    public void joinSuccessIfNoDuplicateUserEmail() {
        // given
        UserSignUpRequest request = new UserSignUpRequest(email, password);

        // when
        UserSignUpResponse response = userService.join(request);

        // then
        assertThat(response.getEmail())
                .isEqualTo(email);
        assertThat(response.getCreatedAt())
                .isBefore(LocalDateTime.now());
    }

    @Test
    @DisplayName("회원 아이디가 아닌 경우 로그인 실패")
    public void logInFailIfNotFoundEmail() {
        // given
        UserSignUpRequest request = new UserSignUpRequest(email, password);
        userService.join(request);

        String emailForLogIn = "yeasung67@gmail.com";
        String passwordForLogIn = "!@test1234";
        UserLoginRequest loginRequest = new UserLoginRequest(emailForLogIn, passwordForLogIn);

        // when, then
        assertThatThrownBy(() -> userService.login(loginRequest))
                .isInstanceOf(UserLoginFailException.class);
    }

    @Test
    @DisplayName("아이디는 일치하지만 비밀번호가 일치하지 않는 경우 로그인 실패")
    public void logInFailIfNotEqualToUserPassword() {
        // given
        UserSignUpRequest request = new UserSignUpRequest(email, password);
        userService.join(request);

        String emailForLogIn = "test@gmail.com";
        String passwordForLogIn = "@@12345";
        UserLoginRequest loginRequest = new UserLoginRequest(emailForLogIn, passwordForLogIn);

        // when, then
        assertThatThrownBy(() -> userService.login(loginRequest))
                .isInstanceOf(UserLoginFailException.class);
    }

    @Test
    @DisplayName("아이디와 비밀번호가 일치하는 경우 로그인 성공")
    public void logInSuccessIfEqualToUserData() {
        // given
        UserSignUpRequest request = new UserSignUpRequest(email, password);
        userService.join(request);

        String emailForLogIn = "test@gmail.com";
        String passwordForLogIn = "!@test1234";
        UserLoginRequest loginRequest = new UserLoginRequest(emailForLogIn, passwordForLogIn);

        // when
        UserLoginResponse loginResponse = userService.login(loginRequest);

        // then
        assertThat(loginResponse.getUserEmail())
                .isEqualTo(email);
        assertThat(loginResponse.getLoginAt())
                .isBefore(LocalDateTime.now());
    }
}
