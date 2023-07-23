package kr.co.prgrms.clone.user.domain;

import kr.co.prgrms.clone.global.error.ErrorCode;
import kr.co.prgrms.clone.global.util.ApplicationUtils;
import kr.co.prgrms.clone.membership.domain.MembershipType;
import kr.co.prgrms.clone.user.exception.impl.UserLoginFailException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class User {

    private final Integer id;
    private final UUID uuid;
    private String email;
    private String password;
    private Integer membershipTypeId;
    private final UUID cartId;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime loginAt;

    public User(String email, String password) {
        this.id = null;
        this.uuid = ApplicationUtils.createUUID();
        this.email = email;
        this.password = password;
        this.membershipTypeId = MembershipType.NO_ASSIGNMENT.getId();
        this.cartId = ApplicationUtils.createUUID();
        this.createdAt = null;
    }

    public void checkPasswordEquality(String password) {
        if (!this.password.equals(password)) {
            throw new UserLoginFailException(ErrorCode.INCORRECT_PASSWORD);
        }
    }

    public byte[] changeUserUuidToByteArray() {
        return this.uuid
                .toString()
                .getBytes();
    }

    public byte[] changeCartIdToByteArray() {
        return this.uuid
                .toString()
                .getBytes();
    }
}
