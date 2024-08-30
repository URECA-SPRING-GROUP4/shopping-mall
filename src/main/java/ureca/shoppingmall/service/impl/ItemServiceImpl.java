package ureca.shoppingmall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        if (item.isPresent()) {
            return Optional.of(convertToDto(item.orElse(null)));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void saveItem(ItemDto itemDto) {
        Item item = convertToEntity(itemDto);
        itemRepository.save(item);
    }

    @Override
    public List<ItemDto> findItemWithLowStock(int quantity) {
        List<Item> allItems = itemRepository.findAll();
        List<ItemDto> lowStockItems = new ArrayList<>();
        for (Item item : allItems) {
            if (item.getStockQuantity() < quantity) {
                lowStockItems.add(convertToDto(item));
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
                itemsInPriceRange.add(convertToDto(item));
            }
        }
        return itemsInPriceRange;
    }

    private ItemDto convertToDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setPrice(item.getPrice());
        dto.setDescription(item.getDescription());
        dto.setStockQuantity(String.valueOf(item.getStockQuantity()));
        return dto;
    }

    private Item convertToEntity(ItemDto dto) {
        Item item = new Item();
        item.setId(dto.getId());
        item.setName(dto.getName());
        item.setPrice(dto.getPrice());
        item.setDescription(dto.getDescription());
        item.setStockQuantity(Integer.parseInt(dto.getStockQuantity()));
        return item;
    }
}
