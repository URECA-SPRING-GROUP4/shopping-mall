package ureca.shoppingmall.domain.order.dto;

import lombok.Data;
import ureca.shoppingmall.common.enums.OrderStatus;
import ureca.shoppingmall.domain.order.Order;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order) {
        id = order.getId();
        userId = order.getUser().getId();
        orderDate = order.getOrderDate();
        status = order.getStatus();
        orderItems = order.getOrderItems().stream()
                .map(orderItem -> new OrderItemDto(orderItem))
                .collect(toList());
    }

    public OrderDto() {
    }
}
