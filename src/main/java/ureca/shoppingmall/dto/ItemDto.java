package ureca.shoppingmall.dto;


import lombok.Data;
import ureca.shoppingmall.domain.Item.Item;

@Data
public class ItemDto {
    private Long id;
    private String name;
    private int price;
    private String description;
    private int stockQuantity;
}
