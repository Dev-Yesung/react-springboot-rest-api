package kr.co.prgrms.clone.order.presentation;

import kr.co.prgrms.clone.order.application.OrderService;
import kr.co.prgrms.clone.order.application.dto.response.OrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{userId}/all")
    public ResponseEntity<OrderResponse> listAll(@PathVariable
                                                 Integer userId) {
        OrderResponse lists = orderService.findAll(userId);

        return ResponseEntity.ok(lists);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<OrderResponse> order(@PathVariable
                                               Integer userId) {
        OrderResponse response = orderService.order(userId);

        return ResponseEntity
                .ok(response);
    }

    @PostMapping("/{userId}/songs/{songId}")
    public ResponseEntity<Void> addSongInOrder(@PathVariable Integer userId,
                                               @PathVariable Integer songId) {
        orderService.addSongInOrder(userId, songId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/{userId}/songs/{songId}")
    public ResponseEntity<Void> deleteSongInOrder(@PathVariable Integer userId,
                                                  @PathVariable Integer songId) {
        orderService.deleteSongInOrder(userId, songId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
