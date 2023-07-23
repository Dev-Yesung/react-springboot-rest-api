package kr.co.prgrms.clone.order.application;

import kr.co.prgrms.clone.global.error.ErrorCode;
import kr.co.prgrms.clone.membership.domain.impl.Credit;
import kr.co.prgrms.clone.membership.repository.MembershipRepository;
import kr.co.prgrms.clone.order.application.dto.response.OrderResponse;
import kr.co.prgrms.clone.order.domain.Order;
import kr.co.prgrms.clone.order.exception.impl.OrderNotFoundException;
import kr.co.prgrms.clone.order.exception.impl.OrderNotInsertException;
import kr.co.prgrms.clone.order.repository.OrderRepository;
import kr.co.prgrms.clone.song.domain.Song;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MembershipRepository membershipRepository;

    public OrderService(OrderRepository orderRepository,
                        MembershipRepository membershipRepository) {
        this.orderRepository = orderRepository;
        this.membershipRepository = membershipRepository;
    }

    @Transactional
    public void addSongInOrder(Integer userId, Integer songId) {
        Order order = orderRepository.findByUserId(userId);
        Integer orderId = order.getId();

        List<Song> songList = orderRepository.findSongByOrderId(orderId);
        boolean isExist = songList.stream()
                .map(Song::getId)
                .anyMatch(id -> id.equals(songId));
        if (isExist) {
            throw new OrderNotInsertException(ErrorCode.ALREADY_EXIST);
        }
        orderRepository.insertSongInOrder(orderId, songId);
    }

    @Transactional
    public void deleteSongInOrder(Integer userId, Integer songId) {
        Order order = orderRepository.findByUserId(userId);
        Integer orderId = order.getId();
        orderRepository.deleteSongInOrder(orderId, songId);
    }

    @Transactional
    public OrderResponse order(Integer userId) {
        Order order = orderRepository.findByUserId(userId);
        Integer orderId = order.getId();
        List<Song> songs = orderRepository.findSongByOrderId(orderId);

        Order newOrder = new Order(orderId, userId);
        newOrder.addOrderList(songs);
        Integer totalCredit = newOrder.calculateTotalCredit();
        Optional<Credit> response = membershipRepository.findCreditByUserId(userId);
        Credit credit = response.orElseThrow(() -> new OrderNotFoundException(ErrorCode.CREDIT_NOT_FOUND));
        credit.subtractAmount(totalCredit);

        membershipRepository.updateCreditByUserId(credit);
        orderRepository.deleteAllByOrderId(orderId);

        return new OrderResponse(songs, totalCredit);
    }

    public OrderResponse findAll(Integer userId) {
        Order order = orderRepository.findByUserId(userId);
        Integer orderId = order.getId();
        List<Song> songs = orderRepository.findSongByOrderId(orderId);
        Order newOrder = new Order(orderId, userId);
        newOrder.addOrderList(songs);
        Integer totalCredit = newOrder.calculateTotalCredit();

        return new OrderResponse(songs, totalCredit);
    }
}
