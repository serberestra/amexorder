package com.amex.order.mapper;

import com.amex.order.dto.ItemRequestDto;
import com.amex.order.dto.ItemResponseDto;
import com.amex.order.exception.NotFoundException;
import com.amex.order.model.AppleItem;
import com.amex.order.model.Item;
import com.amex.order.model.OrangeItem;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ItemMapper {

    private static Set<Item> items = Stream.of(
            AppleItem.builder().build(),
            OrangeItem.builder().build()
    ).collect(Collectors.toCollection(HashSet::new));

    public ItemResponseDto toItemResponseDto(ItemRequestDto item) {
        Item itemToMap = findItem(item.getItemName());
        itemToMap.setQuantity(item.getQuantity());

        return ItemResponseDto.builder()
                .itemName(itemToMap.name())
                .itemPrice(itemToMap.price())
                .quantity(item.getQuantity())
                .total(itemToMap.total())
                .build();
    }

    private Item findItem(String itemName) throws NotFoundException {
        return items.stream()
                .filter(item -> item.name().equalsIgnoreCase(itemName.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("Item '%s' not found", itemName)));
    }

}
