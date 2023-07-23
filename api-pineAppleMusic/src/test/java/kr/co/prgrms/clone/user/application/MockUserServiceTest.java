package kr.co.prgrms.clone.user.application;

import kr.co.prgrms.clone.global.error.ErrorCode;
import kr.co.prgrms.clone.user.application.dto.request.UserLoginRequest;
import kr.co.prgrms.clone.user.application.dto.request.UserSignUpRequest;
import kr.co.prgrms.clone.user.application.dto.response.UserLoginResponse;
import kr.co.prgrms.clone.user.application.dto.response.UserSignUpResponse;
import kr.co.prgrms.clone.membership.domain.MembershipType;
import kr.co.prgrms.clone.user.exception.impl.UserLoginFailException;
import kr.co.prgrms.clone.user.exception.impl.UserSignUpFailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
public class MockUserServiceTest {
    private static final String email = "test@gmail.com";
    private static final String password = "!@test1234";

    @MockBean
    private UserService mockUserService;

    @Test
    @DisplayName("회원이 중복될 경우 이메일 체크에서 예외발생")
    public void throwExceptionWhenCheckEmailIfDuplicateUserEmail() {
        // given
        doThrow(new UserSignUpFailException(ErrorCode.DUPLICATE_USER))
                .when(mockUserService)
                .checkDuplicateEmail(email);

        // when, then
        assertThatThrownBy(() -> mockUserService.checkDuplicateEmail(email))
                .isInstanceOf(UserSignUpFailException.class);
    }

    @Test
    @DisplayName("회원이 중복이 아닐 경우 예외 발생X")
    public void passWhenCheckEmailIfNoDuplicateUserEmail() {
        doNothing().when(mockUserService)
                .checkDuplicateEmail(email);
        // when, then
        assertThatNoException()
                .isThrownBy(() -> mockUserService.checkDuplicateEmail(email));
    }

    @Test
    @DisplayName("회원이 중복될 경우 가입실패")
    public void joinFailIfDuplicateUserEmail() {
        // given
        UserSignUpRequest request = new UserSignUpRequest(email, password);
        doThrow(new UserSignUpFailException(ErrorCode.DUPLICATE_USER))
                .when(mockUserService)
                .join(request);

        // when, then
        assertThatThrownBy(() -> mockUserService.join(request))
                .isInstanceOf(UserSignUpFailException.class);
    }

    @Test
    @DisplayName("중복회원이 없을 경우 가입성공")
    public void joinSuccessIfNoDuplicateUserEmail() {
        // given, when, then
        given(mockUserService.join(any(UserSignUpRequest.class)))
                .willReturn(new UserSignUpResponse(email, LocalDateTime.now()));
    }

    @Test
    @DisplayName("회원 아이디가 아닌 경우 로그인 실패")
    public void logInFailIfNotFoundEmail() {
        // given, when
        UserLoginRequest loginRequest = new UserLoginRequest("yeasung67@gmail.com", "!@test1234");
        doThrow(UserLoginFailException.class)
                .when(mockUserService)
                .login(loginRequest);

        // then
        assertThatThrownBy(() -> mockUserService.login(loginRequest))
                .isInstanceOf(UserLoginFailException.class);
    }

    @Test
    @DisplayName("아이디는 일치하지만 비밀번호가 일치하지 않는 경우 로그인 실패")
    public void logInFailIfNotEqualToUserPassword() {
        // given, when
        UserLoginRequest loginRequest = new UserLoginRequest("test@gmail.com", "@@12345");
        doThrow(UserLoginFailException.class)
                .when(mockUserService)
                .login(loginRequest);

        // then
        assertThatThrownBy(() -> mockUserService.login(loginRequest))
                .isInstanceOf(UserLoginFailException.class);
    }

    @Test
    @DisplayName("아이디와 비밀번호가 일치하는 경우 로그인 성공")
    public void logInSuccessIfEqualToUserData() {
        // given
        String emailForLogIn = "test@gmail.com";
        String passwordForLogIn = "!@test1234";
        UserLoginRequest loginRequest = new UserLoginRequest(emailForLogIn, passwordForLogIn);
        UserLoginResponse loginResponse = new UserLoginResponse(emailForLogIn, MembershipType.NO_ASSIGNMENT, LocalDateTime.now());

        // when
        when(mockUserService.login(loginRequest))
                .thenReturn(loginResponse);

        // then
        assertThat(loginResponse.getUserEmail())
                .isEqualTo(email);
        assertThat(loginResponse.getLoginAt())
                .isBefore(LocalDateTime.now());
    }
}
