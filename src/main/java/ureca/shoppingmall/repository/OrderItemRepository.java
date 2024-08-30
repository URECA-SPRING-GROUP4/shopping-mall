package ureca.shoppingmall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ureca.shoppingmall.domain.order.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
}
