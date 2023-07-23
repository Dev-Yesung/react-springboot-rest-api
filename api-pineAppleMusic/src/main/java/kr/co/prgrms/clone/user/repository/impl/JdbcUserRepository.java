package kr.co.prgrms.clone.user.repository.impl;

import kr.co.prgrms.clone.global.error.ErrorCode;
import kr.co.prgrms.clone.global.util.ApplicationUtils;
import kr.co.prgrms.clone.membership.domain.MembershipType;
import kr.co.prgrms.clone.user.domain.User;
import kr.co.prgrms.clone.user.exception.impl.UserLoginFailException;
import kr.co.prgrms.clone.user.exception.impl.UserSignUpFailException;
import kr.co.prgrms.clone.user.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static kr.co.prgrms.clone.global.error.ErrorCode.*;

@Repository
public class JdbcUserRepository implements UserRepository {
    private static final String INSERT_USER = "INSERT INTO users (uuid, email, password, membership_type, cart_id) " +
            "VALUES (UUID_TO_BIN(:uuid) , :email, :password, :membershipType, UUID_TO_BIN(:cartId))";
    private static final String FIND_BY_EMAIL = "SELECT * FROM users WHERE email = :email";
    private static final String UPDATE_MEMBERSHIP_TYPE = "UPDATE users SET membership_type = :membershipId WHERE email = :email";
    private static final String UPDATE_LOGIN_AT = "UPDATE users SET login_at = :loginAt WHERE email = :email";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcUserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User save(User user) {
        var paramMap = createUserParamMap(user);
        try {
            jdbcTemplate.update(INSERT_USER, paramMap);
        } catch (DataAccessException e) {
            throw new UserSignUpFailException(USER_SAVE_FAIL);
        }

        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        var paramMap = Map.of("email", email);
        try {
            User user = jdbcTemplate.queryForObject(FIND_BY_EMAIL, paramMap, userRowMapper);

            return Optional.ofNullable(user);
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateUserMembership(String userEmail, MembershipType type) {
        Integer membershipId = type.getId();
        var paramMap
                = Map.of("membershipId", membershipId, "email", userEmail);
        try {
            jdbcTemplate.update(UPDATE_MEMBERSHIP_TYPE, paramMap);
        } catch (DataAccessException e) {
            throw new UserLoginFailException(USER_NOT_UPDATE);
        }
    }

    @Override
    public void updateLogInAt(String userEmail, LocalDateTime loginAt) {
        var paramMap = Map.of("loginAt", loginAt, "email", userEmail);
        try {
            jdbcTemplate.update(UPDATE_LOGIN_AT, paramMap);
        } catch (DataAccessException e) {
            throw new UserLoginFailException(USER_NOT_LOG_IN);
        }
    }

    private Map<String, Object> createUserParamMap(User user) {
        byte[] uuid = user.changeUserUuidToByteArray();
        String email = user.getEmail();
        String password = user.getPassword();
        Integer membershipType = user.getMembershipTypeId();
        byte[] cartId = user.changeCartIdToByteArray();

        var paramMap = new HashMap<String, Object>();
        paramMap.put("uuid", uuid);
        paramMap.put("email", email);
        paramMap.put("password", password);
        paramMap.put("membershipType", membershipType);
        paramMap.put("cartId", cartId);

        return paramMap;
    }

    RowMapper<User> userRowMapper = (rs, num) -> {
        Integer id = rs.getInt("id");
        UUID userUUID = ApplicationUtils.toUUID(rs.getBytes("uuid"));
        String email = rs.getString("email");
        String password = rs.getString("password");
        int membershipType = rs.getInt("membership_type");
        UUID cartId = ApplicationUtils.toUUID(rs.getBytes("cart_id"));
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();
        LocalDateTime loginAt = rs.getTimestamp("login_at").toLocalDateTime();

        return new User(id, userUUID, email,
                password, membershipType, cartId,
                createdAt, updatedAt, loginAt);
    };
}
