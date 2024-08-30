package ureca.shoppingmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ureca.shoppingmall.common.mapper.ItemMapper;
import ureca.shoppingmall.domain.Item.Item;
import ureca.shoppingmall.dto.ItemDto;
import ureca.shoppingmall.repository.ItemRepository;
import ureca.shoppingmall.service.ItemService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public List<ItemDto> findItems() {
        return itemRepository.findAllByOrderByIdDesc();
    }

    @Override
    public Optional<ItemDto> findOne(Long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        // ItemMapper를 사용하여 엔티티를 DTO로 변환
        return item.map(ItemMapper::toDto);
    }

    @Override
    public void saveItem(ItemDto itemDto) {
        Item item = new Item(itemDto.getName(), itemDto.getPrice(), itemDto.getDescription(), itemDto.getStockQuantity());
        itemRepository.save(item);
    }

    @Override
    public List<ItemDto> findItemWithLowStock(int quantity) {
        List<Item> allItems = itemRepository.findAll();
        List<ItemDto> lowStockItems = new ArrayList<>();
        for (Item item : allItems) {
            if (item.getStockQuantity() < quantity) {
                // ItemMapper를 사용하여 엔티티를 DTO로 변환
                lowStockItems.add(ItemMapper.toDto(item));
            }
        }
        return lowStockItems;
    }

    @Override
    public List<ItemDto> findItemByPriceRange(int minPrice, int maxPrice) {
        List<Item> allItems = itemRepository.findAll();
        List<ItemDto> itemsInPriceRange = new ArrayList<>();
        for (Item item : allItems) {
            if (item.getPrice() >= minPrice && item.getPrice() <= maxPrice) {
                // ItemMapper를 사용하여 엔티티를 DTO로 변환
                itemsInPriceRange.add(ItemMapper.toDto(item));
            }
        }
        return itemsInPriceRange;
    }
}
