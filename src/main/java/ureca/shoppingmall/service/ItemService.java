package ureca.shoppingmall.service;

import ureca.shoppingmall.domain.Item.Item;
import ureca.shoppingmall.dto.ItemDto;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<ItemDto> findItems();
    Optional<ItemDto> findOne(Long itemId);
//    void saveItem(ItemDto item);
    List<ItemDto> findItemWithLowStock(int quantity);
    List<ItemDto> findItemByPriceRange(int minPrice, int maxPrice);
}
