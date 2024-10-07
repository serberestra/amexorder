package com.amex.order.mapper;

import com.amex.order.dto.ItemResponseDto;
import com.amex.order.entity.OrderEntity;
import com.amex.order.model.AppleItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderDtoMapperTest {

    private OrderDtoMapper orderDtoMapper;

    @BeforeEach
    void setUp() {
        orderDtoMapper = new OrderDtoMapper();
    }

    @Test
    @DisplayName("When Valid Item Then Valid Item Entity")
    public void whenValidItemThenValidItemEntity() {
        var apple = AppleItem.builder().build();
        apple.setQuantity(10);
        var appleResponse = ItemResponseDto.builder()
                .itemName(apple.name())
                .itemPrice(apple.price())
                .quantity(apple.quantity())
                .total(apple.total())
                .build();

        var result = orderDtoMapper.toAmexOrderResponseDto(new OrderEntity(1L, 100.0), Set.of(appleResponse));

        assertEquals(1L, result.getId());
        assertEquals(100.0, result.getTotalOrder());
        assertEquals(1, result.getItems().size());
    }

}