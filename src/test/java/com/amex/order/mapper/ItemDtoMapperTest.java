package com.amex.order.mapper;

import com.amex.order.entity.ItemEntity;
import com.amex.order.model.AppleItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemDtoMapperTest {

    private ItemDtoMapper itemDtoMapper;

    @BeforeEach
    void setUp() {
        itemDtoMapper = new ItemDtoMapper();
    }

    @Test
    @DisplayName("When Valid Map Item Then Valid Response DTO")
    public void validMapItemToResponseDTO() {
        var apple = AppleItem.builder().build();
        apple.setQuantity(10);

        var result = itemDtoMapper.toItemResponseDto(apple);

        assertEquals(apple.name(), result.getItemName());
        assertEquals(apple.price(), result.getItemPrice());
        assertEquals(apple.quantity(), result.getQuantity());
        assertEquals(apple.total(), result.getTotal());
    }

    @Test
    @DisplayName("When Valid Map Item Entity Then Valid Response DTO")
    public void validMapItemEntityToResponseDTO() {
        var apple = AppleItem.builder().build();
        apple.setQuantity(10);
        var appleEntity = new ItemEntity(1L, 1L, apple.name(), apple.price(), apple.quantity(), apple.total());

        var result = itemDtoMapper.toItemResponseDto(appleEntity);

        assertEquals(apple.name(), result.getItemName());
        assertEquals(apple.price(), result.getItemPrice());
        assertEquals(apple.quantity(), result.getQuantity());
        assertEquals(apple.total(), result.getTotal());
    }

}