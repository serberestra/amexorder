package com.amex.order.service;

import com.amex.order.dto.AmexOrderRequestDto;
import com.amex.order.dto.AmexOrderResponseDto;
import com.amex.order.entity.ItemEntity;
import com.amex.order.entity.OrderEntity;
import com.amex.order.exception.NotFoundException;
import com.amex.order.mapper.ItemDtoMapper;
import com.amex.order.mapper.ItemEntityMapper;
import com.amex.order.mapper.OrderDtoMapper;
import com.amex.order.model.AppleItem;
import com.amex.order.model.Item;
import com.amex.order.model.OrangeItem;
import com.amex.order.repository.ItemRepository;
import com.amex.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private static final Set<Item> items = Stream.of(
            AppleItem.builder().build(),
            OrangeItem.builder().build()
    ).collect(Collectors.toCollection(HashSet::new));

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final ItemEntityMapper itemEntityMapper;
    private final ItemDtoMapper itemDtoMapper;
    private final OrderDtoMapper orderDtoMapper;

    private Item findItem(String itemName) throws NotFoundException {
        return items.stream()
                .filter(item -> item.name().equalsIgnoreCase(itemName.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("Item '%s' not found", itemName)));
    }

    @Override
    @Transactional
    public AmexOrderResponseDto submit(AmexOrderRequestDto amexOrderRequestDto) {
        OrderEntity orderEntity = new OrderEntity();

        var itemEntities = amexOrderRequestDto.getItems()
                .stream()
                .map(itemRequestDto -> {
                    var item = findItem(itemRequestDto.getItemName());
                    item.setQuantity(itemRequestDto.getQuantity());
                    return itemEntityMapper.toItemEntity(item);
                })
                .collect(Collectors.toSet());

        orderEntity.setTotalOrder(
                itemEntities.stream().mapToDouble(ItemEntity::getTotal).sum()
        );

        var orderResult = orderRepository.save(orderEntity);
        itemEntities.forEach(itemEntity -> itemEntity.setOrderId(orderResult.getId()));
        var itemResult = itemRepository.saveAll(itemEntities);
        var itemResponse = itemResult.stream().map(itemDtoMapper::toItemResponseDto).collect(Collectors.toSet());

        return orderDtoMapper.toAmexOrderResponseDto(orderResult, itemResponse);
    }

    @Override
    public AmexOrderResponseDto fetchOrderById(Long orderId) {
        var fetchedOrder = orderRepository.findById(orderId).orElseThrow(() -> new NotFoundException(String.format("Order '%s' not found", orderId)));
        var fetchedItems = itemRepository.findByOrderId(orderId);

        var itemResponse = fetchedItems.stream().map(itemDtoMapper::toItemResponseDto).collect(Collectors.toSet());
        return orderDtoMapper.toAmexOrderResponseDto(fetchedOrder, itemResponse);
    }

    @Override
    public List<AmexOrderResponseDto> fetchAll() {
        var fetchedOrders = orderRepository.findAll();
        if (fetchedOrders.isEmpty()) {
            throw new NotFoundException("No orders found");
        }
        return fetchedOrders
                .stream()
                .map(order -> {
                    var orderItems = itemRepository.findByOrderId(order.getId());
                    var itemResponse = orderItems.stream().map(itemDtoMapper::toItemResponseDto).collect(Collectors.toSet());
                    return orderDtoMapper.toAmexOrderResponseDto(order, itemResponse);
                }).collect(Collectors.toList());
    }

}
