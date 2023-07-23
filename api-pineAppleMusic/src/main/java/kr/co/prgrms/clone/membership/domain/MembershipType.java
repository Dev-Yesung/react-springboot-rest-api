package kr.co.prgrms.clone.membership.domain;

import kr.co.prgrms.clone.global.error.exception.UnableToResolveTypeException;

import java.util.Arrays;

public enum MembershipType {
    NO_ASSIGNMENT(0),
    SUBSCRIPTION(1),
    CREDIT(2);
    private final Integer id;

    MembershipType(Integer id) {
        this.id = id;
    }

    public static MembershipType resolve(Integer membershipType) {
        return Arrays.stream(values())
                .filter((type) -> type.getId().equals(membershipType))
                .findFirst()
                .orElseThrow(() -> new UnableToResolveTypeException("유효하지 않은 맴버십입니다."));
    }

    public Integer getId() {
        return id;
    }
}
