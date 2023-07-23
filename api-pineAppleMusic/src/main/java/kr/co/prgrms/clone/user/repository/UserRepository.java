package kr.co.prgrms.clone.user.repository;

import kr.co.prgrms.clone.membership.domain.MembershipType;
import kr.co.prgrms.clone.user.domain.User;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findByEmail(String email);

    void updateUserMembership(String userEmail, MembershipType type);

    void updateLogInAt(String userEmail, LocalDateTime loginAt);
}
