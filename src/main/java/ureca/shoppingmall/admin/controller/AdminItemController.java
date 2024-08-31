package ureca.shoppingmall.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ureca.shoppingmall.admin.service.AdminItemService;
import ureca.shoppingmall.domain.Item.Item;
import ureca.shoppingmall.domain.Item.dto.ItemDto;

import java.util.List;

@Controller
@ResponseBody
@RequiredArgsConstructor
@RequestMapping("/item")
public class AdminItemController {

    private final AdminItemService itemService;

    @GetMapping("/list")
    public List<ItemDto> itemList() {
        return itemService.itemList();
    }

    @GetMapping("/detail")
    public ItemDto detailItem(@RequestParam("id") Long id) {
        return itemService.detailItem(id);
    }

    @PostMapping("/insert")
    public ItemDto insertItem(@RequestBody Item item) {
        System.out.println("client post " + item);
        return itemService.insertItem(item);
    }

    @PostMapping("/update")
    public ItemDto updateItem(Item item) {
        System.out.println("client post " + item);
        return itemService.updateItem(item);
    }

    @GetMapping("/delete")
    public void deleteItem(@RequestParam("id") Long id) {
        itemService.deleteItem(id);
    }
}
