package kr.co.prgrms.clone.membership.application;

import kr.co.prgrms.clone.membership.application.dto.request.CreditPurchaseRequest;
import kr.co.prgrms.clone.membership.application.dto.request.SubscriptionPurchaseRequest;
import kr.co.prgrms.clone.membership.application.dto.response.CreditPurchaseResponse;
import kr.co.prgrms.clone.membership.application.dto.response.SubscriptionPurchaseResponse;
import kr.co.prgrms.clone.membership.domain.PurchaseAmount;
import kr.co.prgrms.clone.membership.domain.PurchaseMonth;
import kr.co.prgrms.clone.membership.domain.impl.Credit;
import kr.co.prgrms.clone.membership.domain.impl.Subscription;
import kr.co.prgrms.clone.membership.exception.impl.MembershipPurchaseFailException;
import kr.co.prgrms.clone.membership.repository.MembershipRepository;
import kr.co.prgrms.clone.user.domain.User;
import kr.co.prgrms.clone.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static kr.co.prgrms.clone.global.error.ErrorCode.CREDIT_NOT_FOUND;
import static kr.co.prgrms.clone.global.error.ErrorCode.SUBSCRIPTION_NOT_FOUND;
import static kr.co.prgrms.clone.global.error.ErrorCode.USER_NOT_FOUND;

@Service
public class MembershipService {
    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;

    public MembershipService(MembershipRepository membershipRepository,
                             UserRepository userRepository) {
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CreditPurchaseResponse purchaseCredits(CreditPurchaseRequest request) {
        String userEmail = request.getEmail();
        Optional<User> userResponse = userRepository.findByEmail(userEmail);
        Credit newCredit = userResponse.map((user) -> {
            Integer userId = user.getId();
            Credit credit = new Credit(userId);
            membershipRepository.saveCreditByUserId(credit);

            return credit;
        }).orElseThrow(() -> new MembershipPurchaseFailException(USER_NOT_FOUND));
        Integer updatedAmount = newCredit.getAmount();

        return new CreditPurchaseResponse(userEmail, updatedAmount);
    }

    @Transactional
    public CreditPurchaseResponse updateCredits(CreditPurchaseRequest request) {
        String userEmail = request.getEmail();
        Optional<User> userResponse = userRepository.findByEmail(userEmail);
        Credit updatedCredit = userResponse.map((user) -> {
            Integer userId = user.getId();
            PurchaseAmount amount = request.getRequestAmount();

            return addCredits(userId, amount);
        }).orElseThrow(() -> new MembershipPurchaseFailException(USER_NOT_FOUND));
        Integer updatedAmount = updatedCredit.getAmount();

        return new CreditPurchaseResponse(userEmail, updatedAmount);
    }

    @Transactional
    public SubscriptionPurchaseResponse purchaseSubscription(SubscriptionPurchaseRequest request) {
        String userEmail = request.getEmail();
        Optional<User> userResponse = userRepository.findByEmail(userEmail);
        Subscription newSubscription = userResponse.map((user) -> {
            Integer userId = user.getId();
            Subscription subscription = new Subscription(userId);
            membershipRepository.saveSubscriptionByUserId(subscription);

            return subscription;
        }).orElseThrow(() -> new MembershipPurchaseFailException(USER_NOT_FOUND));
        LocalDateTime expiredAt = newSubscription.getExpiredAt();

        return new SubscriptionPurchaseResponse(userEmail, expiredAt);
    }

    @Transactional
    public SubscriptionPurchaseResponse renewalSubscription(SubscriptionPurchaseRequest request) {
        String userEmail = request.getEmail();
        Optional<User> userResponse = userRepository.findByEmail(userEmail);
        Subscription updatedSubscription = userResponse.map((user) -> {
            Integer userId = user.getId();
            PurchaseMonth requestMonth = request.getRequestMonth();

            return addExpiredAt(userId, requestMonth);
        }).orElseThrow(() -> new MembershipPurchaseFailException(USER_NOT_FOUND));
        LocalDateTime expiredAt = updatedSubscription.getExpiredAt();

        return new SubscriptionPurchaseResponse(userEmail, expiredAt);
    }

    private Credit addCredits(Integer userId, PurchaseAmount requestAmount) {
        Integer amount = requestAmount.getAmount();
        Optional<Credit> creditResponse = membershipRepository.findCreditByUserId(userId);

        return creditResponse.map((credit) -> {
            credit.addPurchasedAmount(amount);
            membershipRepository.updateCreditByUserId(credit);

            return credit;
        }).orElseThrow(() -> new MembershipPurchaseFailException(CREDIT_NOT_FOUND));
    }

    private Subscription addExpiredAt(Integer userId, PurchaseMonth requestMonth) {
        Optional<Subscription> subscriptionResponse = membershipRepository.findSubscriptionByUserId(userId);
        Integer month = requestMonth.getMonth();

        return subscriptionResponse.map((subscription) -> {
            subscription.addExpiredAt(month);
            membershipRepository.updateSubscriptionByUserId(subscription);

            return subscription;
        }).orElseThrow(() -> new MembershipPurchaseFailException(SUBSCRIPTION_NOT_FOUND));
    }
}
