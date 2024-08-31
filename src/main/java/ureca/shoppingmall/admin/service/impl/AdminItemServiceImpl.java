package ureca.shoppingmall.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ureca.shoppingmall.admin.repository.AdminItemRepository;
import ureca.shoppingmall.admin.service.ItemService;
import ureca.shoppingmall.domain.Item.Item;
import ureca.shoppingmall.dto.ItemDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminItemServiceImpl implements ItemService {

    private final AdminItemRepository itemRepository;

    @Override
    public List<ItemDto> itemList() {
        List<ItemDto> itemDtoList = new ArrayList<>();
        List<Item> itemList = itemRepository.findAll();

        itemList.forEach(item -> {
            ItemDto itemDto = new ItemDto();

            itemDto.setId(item.getId());
            itemDto.setName(item.getName());
            itemDto.setPrice(item.getPrice());
            itemDto.setDescription(item.getDescription());
            itemDto.setStockQuantity(item.getStockQuantity());

            itemDtoList.add(itemDto);
        });

        return itemDtoList;
    }

    @Override
    public ItemDto detailItem(Long id) {

        Optional<Item> item = itemRepository.findById(id);
        ItemDto itemDto = new ItemDto();

        if (item.isPresent()) {
            Item el = item.get();

            itemDto.setId(el.getId());
            itemDto.setName(el.getName());
            itemDto.setPrice(el.getPrice());
            itemDto.setDescription(el.getDescription());
            itemDto.setStockQuantity(el.getStockQuantity());

        } else {
            return null;
        }

        return itemDto;
    }

    @Override
    public ItemDto insertItem(Item item) {
        Item save = itemRepository.save(item);
        System.out.println("Save , " + save);

        ItemDto itemDto = new ItemDto();

        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setPrice(item.getPrice());
        System.out.println("get Price" + item.getPrice());
        itemDto.setDescription(item.getDescription());
        itemDto.setStockQuantity(item.getStockQuantity());

        return itemDto;
    }

    @Override
    public ItemDto updateItem(Item item) {
        itemRepository.save(item);
        ItemDto itemDto = new ItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setPrice(item.getPrice());
        itemDto.setDescription(item.getDescription());
        itemDto.setStockQuantity(item.getStockQuantity());

        return itemDto;
    }

    @Override
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
