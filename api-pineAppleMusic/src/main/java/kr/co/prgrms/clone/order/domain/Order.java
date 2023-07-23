package kr.co.prgrms.clone.order.domain;

import kr.co.prgrms.clone.song.domain.Song;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class Order {
    private final Integer id;
    private final Integer userId;
    private List<Song> orderList;

    public Order(Integer id, Integer userId) {
        this.id = id;
        this.userId = userId;
        this.orderList = new ArrayList<>();
    }

    public Integer calculateTotalCredit() {
        return orderList.stream()
                .map(Song::getPrice)
                .reduce(0, Integer::sum);
    }

    public void addOrderList(List<Song> orderList) {
        this.orderList.addAll(orderList);
    }
}
