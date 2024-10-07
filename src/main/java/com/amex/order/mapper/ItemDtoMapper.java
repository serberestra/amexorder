package com.amex.order.mapper;

import com.amex.order.dto.ItemResponseDto;
import com.amex.order.entity.ItemEntity;
import com.amex.order.model.Item;
import org.springframework.stereotype.Component;

@Component
public class ItemDtoMapper {

    public ItemResponseDto toItemResponseDto(Item item) {

        return ItemResponseDto.builder()
                .itemName(item.name())
                .itemPrice(item.price())
                .quantity(item.quantity())
                .total(item.total())
                .build();
    }

    public ItemResponseDto toItemResponseDto(ItemEntity item) {

        return ItemResponseDto.builder()
                .itemName(item.getItemName())
                .itemPrice(item.getItemPrice())
                .quantity(item.getQuantity())
                .total(item.getTotal())
                .build();
    }



}
