package kr.co.prgrms.clone.order.repository;

import kr.co.prgrms.clone.order.application.dto.response.OrderResponse;
import kr.co.prgrms.clone.order.domain.Order;
import kr.co.prgrms.clone.song.domain.Song;

import java.util.List;

public interface OrderRepository {
    List<Song> findSongByOrderId(Integer orderId);

    void insertSongInOrder(Integer orderId, Integer songId);

    Order findByUserId(Integer userId);

    void deleteAllByOrderId(Integer orderId);

    void deleteSongInOrder(Integer orderId, Integer songId);

    void createOrder(Integer userId);
}
