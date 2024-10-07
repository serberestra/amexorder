package com.amex.order.mapper;

import com.amex.order.dto.AmexOrderResponseDto;
import com.amex.order.dto.ItemResponseDto;
import com.amex.order.entity.OrderEntity;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class OrderDtoMapper {

    public AmexOrderResponseDto toAmexOrderResponseDto(OrderEntity orderEntity, Set<ItemResponseDto> itemResponseDto) {
        AmexOrderResponseDto amexOrderResponseDto = new AmexOrderResponseDto();
        amexOrderResponseDto.setId(orderEntity.getId());
        amexOrderResponseDto.setTotalOrder(orderEntity.getTotalOrder());
        amexOrderResponseDto.setItems(itemResponseDto);

        return amexOrderResponseDto;
    }

}
