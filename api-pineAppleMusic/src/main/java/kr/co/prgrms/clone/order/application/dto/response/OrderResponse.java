package kr.co.prgrms.clone.order.application.dto.response;

import kr.co.prgrms.clone.song.domain.Song;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class OrderResponse {
    private final List<Song> orderList;
    private final Integer totalPrice;

    public OrderResponse() {
        this.orderList = null;
        this.totalPrice = null;
    }
}
