package ureca.shoppingmall.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private Long id;
    private Long itemId;
    private int quantity;
}
