package ureca.shoppingmall.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ureca.shoppingmall.domain.Item.Item;
import ureca.shoppingmall.domain.order.Order;
import ureca.shoppingmall.domain.order.OrderItem;
import ureca.shoppingmall.domain.user.User;
import ureca.shoppingmall.dto.OrderDto;
import ureca.shoppingmall.dto.OrderItemDto;
import ureca.shoppingmall.repository.ItemRepository;
import ureca.shoppingmall.repository.OrderRepository;
import ureca.shoppingmall.repository.UserRepository;
import ureca.shoppingmall.service.OrderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public OrderDto createOrder(Long userId, Long itemId, int count) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("no user"));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("no item"));

        OrderItem orderItem = OrderItem.createOrderItem(item, count);

        Order order = Order.createOrder(user, orderItem);

        Order findOrder = orderRepository.save(order);
        return new OrderDto(findOrder);
    }

    @Override
    public Optional<OrderDto> findOrderById(long orderId) {
        return orderRepository.findById(orderId)
                .map(this::createOrderDto);
    }

    @Override
    public List<OrderDto> findOrdersByUser(User user) {
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            orderDtos.add(createOrderDto(order));
        }

        return orderDtos;
    }

    private OrderDto createOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserId(order.getUser().getId());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setStatus(order.getStatus());

        List<OrderItemDto> orderItemDtos = new ArrayList<>();
        for (OrderItem orderItem : order.getOrderItems()) {
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setId(orderItem.getId());
            orderItemDto.setItemId(orderItem.getItem().getId());
            orderItemDto.setQuantity(orderItem.getQuantity());
            orderItemDtos.add(orderItemDto);
        }
        orderDto.setOrderItems(orderItemDtos);

        return orderDto;
    }
}
