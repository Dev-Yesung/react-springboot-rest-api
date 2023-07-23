package kr.co.prgrms.clone.membership.repository.impl;

import kr.co.prgrms.clone.global.error.ErrorCode;
import kr.co.prgrms.clone.membership.domain.impl.Credit;
import kr.co.prgrms.clone.membership.domain.impl.Subscription;
import kr.co.prgrms.clone.membership.exception.impl.MembershipPurchaseFailException;
import kr.co.prgrms.clone.membership.repository.MembershipRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcMembershipRepository implements MembershipRepository {
    private static final String FIND_CREDIT_BY_USER_ID
            = "SELECT * FROM credits WHERE user_id = :userId";
    private static final String FIND_SUBSCRIPTION_BY_USER_ID
            = "SELECT * FROM subscriptions WHERE user_id = :userId";
    private static final String INSERT_CREDIT_BY_USER_ID
            = "INSERT INTO credits (user_id, amount) VALUES (:userId, :amount)";
    private static final String INSERT_SUBSCRIPTION_BY_USER_ID
            = "INSERT INTO subscriptions (user_id, expired_at) VALUES (:userId, :expiredAt)";
    private static final String UPDATE_CREDIT_BY_USER_ID
            = "UPDATE credits SET amount = :amount WHERE user_id = :userId";
    private static final String UPDATE_SUBSCRIPTION_BY_USER_ID
            = "UPDATE subscriptions SET expired_at = TIMESTAMP(:expiredAt) WHERE user_id = :userId";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcMembershipRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Credit> findCreditByUserId(Integer userId) {
        var paramMap = Map.of("userId", userId);
        try {
            Credit credit = jdbcTemplate.queryForObject(FIND_CREDIT_BY_USER_ID, paramMap, creditRowMapper);

            return Optional.ofNullable(credit);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Subscription> findSubscriptionByUserId(Integer userId) {
        var paramMap = Map.of("userId", userId);
        try {
            Subscription subscription = jdbcTemplate.queryForObject(FIND_SUBSCRIPTION_BY_USER_ID, paramMap, subscriptionRowMapper);

            return Optional.ofNullable(subscription);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void saveCreditByUserId(Credit credit) {
        Integer userId = credit.getUserId();
        Integer amount = credit.getAmount();
        var paramMap = Map.of(
                "userId", userId,
                "amount", amount
        );

        try {
            jdbcTemplate.update(INSERT_CREDIT_BY_USER_ID, paramMap);
        } catch (DataAccessException e) {
            throw new MembershipPurchaseFailException(ErrorCode.CREDIT_SAVE_FAIL);
        }
    }

    @Override
    public void saveSubscriptionByUserId(Subscription subscription) {
        Integer userId = subscription.getUserId();
        LocalDateTime expiredAt = subscription.getExpiredAt();
        var paramMap = Map.of(
                "userId", userId,
                "expiredAt", expiredAt
        );

        try {
            jdbcTemplate.update(INSERT_SUBSCRIPTION_BY_USER_ID, paramMap);
        } catch (DataAccessException e) {
            throw new MembershipPurchaseFailException(ErrorCode.SUBSCRIPTION_SAVE_FAIL);
        }
    }

    @Override
    public void updateCreditByUserId(Credit credit) {
        Integer userId = credit.getUserId();
        Integer amount = credit.getAmount();
        var paramMap = Map.of(
                "userId", userId,
                "amount", amount
        );

        try {
            jdbcTemplate.update(UPDATE_CREDIT_BY_USER_ID, paramMap);
        } catch (DataAccessException e) {
            throw new MembershipPurchaseFailException(ErrorCode.CREDIT_UPDATE_FAIL);
        }
    }

    @Override
    public void updateSubscriptionByUserId(Subscription subscription) {
        Integer userId = subscription.getUserId();
        LocalDateTime expiredAt = subscription.getExpiredAt();
        var paramMap = Map.of(
                "userId", userId,
                "expiredAt", expiredAt
        );

        try {
            jdbcTemplate.update(UPDATE_SUBSCRIPTION_BY_USER_ID, paramMap);
        } catch (DataAccessException e) {
            throw new MembershipPurchaseFailException(ErrorCode.SUBSCRIPTION_UPDATE_FAIL);
        }
    }

    RowMapper<Credit> creditRowMapper = (rs, num) -> {
        Integer id = rs.getInt("id");
        Integer userId = rs.getInt("user_id");
        Integer amount = rs.getInt("amount");

        return new Credit(id, userId, amount);
    };

    RowMapper<Subscription> subscriptionRowMapper = (rs, num) -> {
        Integer id = rs.getInt("id");
        Integer userId = rs.getInt("user_id");
        LocalDateTime expiredAt = rs.getTimestamp("expired_at").toLocalDateTime();

        return new Subscription(id, userId, expiredAt);
    };
}
