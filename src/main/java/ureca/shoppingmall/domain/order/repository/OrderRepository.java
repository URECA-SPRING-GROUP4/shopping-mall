package ureca.shoppingmall.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ureca.shoppingmall.domain.order.Order;
import ureca.shoppingmall.domain.user.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 사용자 별 주문 조회
    List<Order> findByUser(User user);

    // 주문 상태별 조회
//    List<Order> findByUser(User user, OrderStatus status);

    // 주문과 주문 아이템을 함께 조회
    @Query("select o from Order o join fetch o.orderItems oi join fetch oi.item where o.id = :orderId")
    Order findOrderWithItems(@Param("orderId") Long orderId);
}
