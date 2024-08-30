package ureca.shoppingmall.admin.service;

import ureca.shoppingmall.admin.dto.ItemDto;
import ureca.shoppingmall.domain.Item.Item;

import java.util.List;

public interface ItemService {

    List<ItemDto> itemList();

    ItemDto detailItem(Long id);

    ItemDto insertItem(Item item);

    ItemDto updateItem(Item item);

    void deleteItem(Long id);
}
