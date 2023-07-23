package kr.co.prgrms.clone.user.application;

import kr.co.prgrms.clone.global.error.ErrorCode;
import kr.co.prgrms.clone.order.repository.OrderRepository;
import kr.co.prgrms.clone.user.application.dto.request.UserLoginRequest;
import kr.co.prgrms.clone.user.application.dto.request.UserSignUpRequest;
import kr.co.prgrms.clone.user.application.dto.response.UserLoginResponse;
import kr.co.prgrms.clone.user.application.dto.response.UserSignUpResponse;
import kr.co.prgrms.clone.membership.domain.MembershipType;
import kr.co.prgrms.clone.user.domain.User;
import kr.co.prgrms.clone.user.exception.impl.UserLoginFailException;
import kr.co.prgrms.clone.user.exception.impl.UserSignUpFailException;
import kr.co.prgrms.clone.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static kr.co.prgrms.clone.global.error.ErrorCode.USER_NOT_FOUND;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public UserService(UserRepository userRepository,
                       OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public UserSignUpResponse join(UserSignUpRequest request) {
        String requestEmail = request.getEmail();
        String requestPassword = request.getPassword();
        User user = new User(requestEmail, requestPassword);

        userRepository.save(user);
        Optional<User> response = userRepository.findByEmail(requestEmail);
        User createdUser = response.orElseThrow(() -> new UserSignUpFailException(USER_NOT_FOUND));
        Integer userId = createdUser.getId();
        orderRepository.createOrder(userId);

        String joinedEmail = createdUser.getEmail();
        LocalDateTime createdAt = createdUser.getCreatedAt();

        return new UserSignUpResponse(joinedEmail, createdAt);
    }

    public Boolean checkDuplicateEmail(String email) {
        Optional<User> response = userRepository.findByEmail(email);
        response.ifPresent((user) -> {
            throw new UserSignUpFailException(ErrorCode.DUPLICATE_USER);
        });

        return true;
    }

    @Transactional
    public UserLoginResponse login(UserLoginRequest request) {
        String requestEmail = request.getEmail();
        String requestPassword = request.getPassword();

        Optional<User> response = userRepository.findByEmail(requestEmail);

        return response.map((user) -> {
            user.checkPasswordEquality(requestPassword);
            String userEmail = user.getEmail();
            MembershipType membershipType = MembershipType.resolve(user.getMembershipTypeId());
            LocalDateTime loginAt = LocalDateTime.now();
            userRepository.updateLogInAt(userEmail, loginAt);

            return new UserLoginResponse(userEmail, membershipType, loginAt);
        }).orElseThrow(() -> new UserLoginFailException(ErrorCode.USER_NOT_FOUND));
    }
}
