package kr.co.prgrms.clone.user.repository;

import kr.co.prgrms.clone.user.domain.User;
import kr.co.prgrms.clone.user.exception.impl.UserSignUpFailException;
import kr.co.prgrms.clone.user.repository.impl.JdbcUserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JdbcUserRepository.class)
class JdbcUserRepositoryTest {

    @Autowired
    private JdbcUserRepository userRepository;
    private static final String email = "test@gmail.com";
    private static final String password = "!@test1234";
    private static User user;

    @BeforeAll
    static void setUp() {
        user = new User(email, password);
    }

    @Test
    @DisplayName("중복 이메일인 경우 저장에 실패합니다.")
    public void throwExceptionIfDuplicateUserEmail() {
        // given
        User dupUser = new User(email, password);
        // when
        userRepository.save(user);
        // then
        assertThatThrownBy(() -> userRepository.save(dupUser))
                .isInstanceOf(UserSignUpFailException.class);
    }

    @Test
    @DisplayName("중복 이메일이 아닌 경우 저장에 성공합니다.")
    public void saveSuccessIfEmailIsNotDuplicated() {
        // when
        User savedUser = userRepository.save(user);
        // then
        assertThat(savedUser.getEmail()).isEqualTo(email);
        assertThat(savedUser.getPassword()).isEqualTo(password);
    }

    @Test
    @DisplayName("존재하지 않는 회원일 경우 빈 값을 반환합니다.")
    public void throwExceptionIfNoExistEmail() {
        // when
        Optional<User> response = userRepository.findByEmail(email);
        // then
        assertThat(response.isEmpty())
                .isEqualTo(true);
    }

    @Test
    @DisplayName("존재하는 회원일 경우 유저를 반환합니다.")
    public void getUserIfExistEmail() {
        // given
        userRepository.save(user);
        // when
        Optional<User> response = userRepository.findByEmail(email);
        // then
        assertThat(response.isPresent()).isEqualTo(true);
        assertThat(response.get().getEmail()).isEqualTo(email);
    }
}
