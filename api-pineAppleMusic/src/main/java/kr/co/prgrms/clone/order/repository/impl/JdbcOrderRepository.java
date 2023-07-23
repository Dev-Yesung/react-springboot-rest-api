package kr.co.prgrms.clone.order.repository.impl;

import kr.co.prgrms.clone.global.error.ErrorCode;
import kr.co.prgrms.clone.order.application.dto.response.OrderResponse;
import kr.co.prgrms.clone.order.domain.Order;
import kr.co.prgrms.clone.order.exception.impl.OrderNotCreateException;
import kr.co.prgrms.clone.order.exception.impl.OrderNotDeleteException;
import kr.co.prgrms.clone.order.exception.impl.OrderNotFoundException;
import kr.co.prgrms.clone.order.exception.impl.OrderNotInsertException;
import kr.co.prgrms.clone.order.repository.OrderRepository;
import kr.co.prgrms.clone.song.domain.Song;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderRepository implements OrderRepository {
    private static final String FIND_BY_USER_ID
            = "SELECT * FROM orders WHERE user_id = :userId";
    private static final String FIND_SONG_BY_ORDER_ID
            = "SELECT B.id, B.title, B.genre_id, B.play_time, B.lyrics, B.url, B.price " +
            "FROM orders_songs AS A JOIN songs AS B ON A.song_id = B.id " +
            "WHERE A.order_id = :orderId";
    private static final String INSERT_SONG_IN_ORDER
            = "INSERT INTO orders_songs (order_id, song_id) VALUES (:orderId, :songId)";
    private static final String DELETE_ALL_BY_ORDER_ID
            = "DELETE FROM orders_songs WHERE order_id = :orderId";
    private static final String DELETE_SONG_IN_ORDER
            = "DELETE FROM orders_songs WHERE order_id = :orderId AND song_id = :songId";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcOrderRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insertSongInOrder(Integer orderId, Integer songId) {
        var paramMap
                = Map.of("orderId", orderId, "songId", songId);
        try {
            jdbcTemplate.update(INSERT_SONG_IN_ORDER, paramMap);
        } catch (DataAccessException e) {
            throw new OrderNotInsertException(ErrorCode.NOT_INSERT_ORDER);
        }
    }

    @Override
    public Order findByUserId(Integer userId) {
        var paramMap
                = Collections.singletonMap("userId", userId);
        try {
            return jdbcTemplate.queryForObject(FIND_BY_USER_ID, paramMap, orderRowMapper);
        } catch (DataAccessException e) {
            throw new OrderNotFoundException(ErrorCode.ORDER_FOUND_FAIL);
        }
    }

    @Override
    public List<Song> findSongByOrderId(Integer orderId) {
        var paramMap
                = Collections.singletonMap("orderId", orderId);
        try {
            return jdbcTemplate.query(FIND_SONG_BY_ORDER_ID, paramMap, songRowMapper);
        } catch (DataAccessException e) {
            throw new OrderNotFoundException(ErrorCode.NOT_FOUND_SONG);
        }
    }

    @Override
    public void deleteAllByOrderId(Integer orderId) {
        var paramMap
                = Collections.singletonMap("orderId", orderId);
        try {
            jdbcTemplate.update(DELETE_ALL_BY_ORDER_ID, paramMap);
        } catch (DataAccessException e) {
            throw new OrderNotDeleteException(ErrorCode.ORDER_DELETE_FAIL);
        }
    }

    @Override
    public void deleteSongInOrder(Integer orderId, Integer songId) {
        var paramMap = Map.of(
                "orderId", orderId,
                "songId", songId);
        try {
            jdbcTemplate.update(DELETE_SONG_IN_ORDER, paramMap);
        } catch (DataAccessException e) {
            throw new OrderNotDeleteException(ErrorCode.ORDER_DELETE_FAIL);
        }
    }

    @Override
    public void createOrder(Integer userId) {
        var paramMap = Collections.singletonMap("userId", userId);
        try {
            jdbcTemplate.update("INSERT INTO orders (user_id) VALUES (:userId)", paramMap);
        } catch (DataAccessException e) {
            throw new OrderNotCreateException(ErrorCode.NOT_CREATE_ORDER);
        }
    }

    RowMapper<Song> songRowMapper = (rs, num) -> {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        int genreId = rs.getInt("genre_id");
        float playTime = rs.getFloat("play_time");
        String lyrics = rs.getString("lyrics");
        String url = rs.getString("url");
        int price = rs.getInt("price");

        return new Song(id, title, genreId, playTime, lyrics, url, price);
    };

    RowMapper<Order> orderRowMapper = (rs, num) -> {
        int id = rs.getInt("id");
        int userId = rs.getInt("user_id");

        return new Order(id, userId);
    };
}
