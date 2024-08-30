package ureca.shoppingmall.common.mapper;


import ureca.shoppingmall.domain.Item.Item;
import ureca.shoppingmall.dto.ItemDto;

public class ItemMapper {

    public static ItemDto toDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setPrice(item.getPrice());
        dto.setDescription(item.getDescription());
        dto.setStockQuantity(item.getStockQuantity());
        return dto;
    }
}

