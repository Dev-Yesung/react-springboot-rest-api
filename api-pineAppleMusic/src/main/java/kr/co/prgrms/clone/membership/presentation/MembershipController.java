package kr.co.prgrms.clone.membership.presentation;

import kr.co.prgrms.clone.membership.application.MembershipService;
import kr.co.prgrms.clone.membership.application.dto.request.CreditPurchaseRequest;
import kr.co.prgrms.clone.membership.application.dto.request.SubscriptionPurchaseRequest;
import kr.co.prgrms.clone.membership.application.dto.response.CreditPurchaseResponse;
import kr.co.prgrms.clone.membership.application.dto.response.SubscriptionPurchaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/memberships")
public class MembershipController {
    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @PostMapping("/credits")
    public ResponseEntity<CreditPurchaseResponse> purchaseCredits(@Validated @RequestBody
                                                                  CreditPurchaseRequest request) {
        CreditPurchaseResponse response = membershipService.purchaseCredits(request);

        return ResponseEntity
                .status(CREATED)
                .body(response);
    }

    @PostMapping("/credits/addition")
    public ResponseEntity<CreditPurchaseResponse> updateCredits(@Validated @RequestBody
                                                                CreditPurchaseRequest request) {
        CreditPurchaseResponse response = membershipService.updateCredits(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/subscriptions")
    public ResponseEntity<SubscriptionPurchaseResponse> purchaseSubscriptions(@Validated @RequestBody
                                                                              SubscriptionPurchaseRequest request) {
        SubscriptionPurchaseResponse response = membershipService.purchaseSubscription(request);

        return ResponseEntity
                .status(CREATED)
                .body(response);
    }

    @PostMapping("/subscriptions/renewal")
    public ResponseEntity<SubscriptionPurchaseResponse> renewalSubscription(@Validated @RequestBody
                                                                            SubscriptionPurchaseRequest request) {
        SubscriptionPurchaseResponse response = membershipService.renewalSubscription(request);

        return ResponseEntity.ok(response);
    }

}
