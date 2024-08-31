package ureca.shoppingmall.service;

import ureca.shoppingmall.domain.user.User;
import ureca.shoppingmall.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderService{

    // 주문 생성
    OrderDto createOrder(Long userId, Long itemId, int count);

    // 주문 조회
    OrderDto findOrderById(long orderId);

    // 사용자의 모든 주문 조회
    List<OrderDto> findOrdersByUser(User user);

}
