package ureca.shoppingmall.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ureca.shoppingmall.dto.OrderDto;
import ureca.shoppingmall.service.OrderService;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderDto order(@RequestParam("userId") Long userId,
                          @RequestParam("itemId") Long itemId,
                          @RequestParam("count") int count) {

        return orderService.createOrder(userId, itemId, count);
    }
}

