package com.amex.order.mapper;

import com.amex.order.model.AppleItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemEntityMapperTest {

    private ItemEntityMapper itemEntityMapper;

    @BeforeEach
    void setUp() {
        itemEntityMapper = new ItemEntityMapper();
    }

    @Test
    @DisplayName("When Valid Item Then Valid Item Entity")
    public void whenValidItemThenValidItemEntity() {
        var apple = AppleItem.builder().build();
        apple.setQuantity(10);

        var result = itemEntityMapper.toItemEntity(apple);

        assertEquals(apple.name(), result.getItemName());
        assertEquals(apple.price(), result.getItemPrice());
        assertEquals(apple.quantity(), result.getQuantity());
        assertEquals(apple.total(), result.getTotal());
    }

}