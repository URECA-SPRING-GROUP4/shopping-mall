package ureca.shoppingmall.domain.order.service;

import ureca.shoppingmall.domain.user.User;
import ureca.shoppingmall.domain.order.dto.OrderDto;

import java.util.List;

public interface OrderService{

    // 주문 생성
    OrderDto createOrder(Long userId, Long itemId, int count);

    // 주문 조회
    OrderDto findOrderById(long orderId);

    // 사용자의 모든 주문 조회
    List<OrderDto> findOrdersByUser(User user);

}
