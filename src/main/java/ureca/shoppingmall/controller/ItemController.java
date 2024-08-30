package ureca.shoppingmall.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ureca.shoppingmall.dto.ItemDto;
import ureca.shoppingmall.service.ItemService;

@Controller
@RequestMapping("/items")
@ResponseBody
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public void saveItem(@RequestBody ItemDto itemDto) {
        itemService.saveItem(itemDto);
    }
}
