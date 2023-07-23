package kr.co.prgrms.clone.membership.repository;

import kr.co.prgrms.clone.membership.domain.impl.Credit;
import kr.co.prgrms.clone.membership.domain.impl.Subscription;

import java.util.Optional;

public interface MembershipRepository {
    Optional<Credit> findCreditByUserId(Integer userId);

    Optional<Subscription> findSubscriptionByUserId(Integer userId);

    void saveCreditByUserId(Credit credit);

    void saveSubscriptionByUserId(Subscription subscription);

    void updateCreditByUserId(Credit credit);

    void updateSubscriptionByUserId(Subscription subscription);
}
