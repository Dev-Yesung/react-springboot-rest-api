package kr.co.prgrms.clone.membership.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Membership {
    private final Integer id;
    private final Integer userId;
}
