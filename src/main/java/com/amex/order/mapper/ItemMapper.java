package com.amex.order.mapper;

import com.amex.order.dto.ItemRequestDto;
import com.amex.order.dto.ItemResponseDto;
import com.amex.order.model.GoodsEnum;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {

    public ItemResponseDto toItemResponseDto(ItemRequestDto item) {
        var itemEnum = GoodsEnum.valueOf(item.getItemName().toUpperCase());
        return ItemResponseDto.builder()
                .itemName(itemEnum.name())
                .itemPrice(itemEnum.getValue())
                .quantity(item.getQuantity())
                .total(this.getTotal(itemEnum, item.getQuantity()))
                .build();
    }

    private Double getTotal(GoodsEnum goodsEnum, Integer quantity) {
        return goodsEnum.getValue() * quantity;
    }

}
