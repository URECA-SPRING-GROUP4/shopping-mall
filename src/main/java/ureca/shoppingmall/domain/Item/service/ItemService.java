package ureca.shoppingmall.domain.Item.service;

import ureca.shoppingmall.domain.Item.dto.ItemDto;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<ItemDto> findItems();
    Optional<ItemDto> findOne(Long itemId);

    void saveItem(ItemDto itemDto);

    //    void saveItem(ItemDto item);
    List<ItemDto> findItemWithLowStock(int quantity);
    List<ItemDto> findItemByPriceRange(int minPrice, int maxPrice);
}
