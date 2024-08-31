package ureca.shoppingmall.domain.Item.dto;


import lombok.Data;

@Data
public class ItemDto {
    private Long id;
    private String name;
    private int price;
    private String description;
    private int stockQuantity;
}
