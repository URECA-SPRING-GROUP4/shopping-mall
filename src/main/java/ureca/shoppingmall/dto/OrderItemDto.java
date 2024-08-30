package ureca.shoppingmall.dto;

import lombok.Data;
import ureca.shoppingmall.domain.order.OrderItem;

@Data
public class OrderItemDto {
    private Long id;
    private Long itemId;
    private int quantity;

    public OrderItemDto(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.itemId = orderItem.getItem().getId();
        this.quantity = orderItem.getQuantity();
    }

    public OrderItemDto() {
    }
}
