package ureca.shoppingmall.dto;

import lombok.Data;
import ureca.shoppingmall.common.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private List<OrderItemDto> orderItems;
}
