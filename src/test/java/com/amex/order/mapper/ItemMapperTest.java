package com.amex.order.mapper;

import com.amex.order.dto.ItemRequestDto;
import com.amex.order.model.GoodsEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.amex.order.OrderConstants.APPLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemMapperTest {

    ItemRequestDto apples;

    private ItemMapper itemMapper;

    @BeforeEach
    void setUp() {
        itemMapper = new ItemMapper();
    }

    @Test
    @DisplayName("Valid Map Item Request DTO To Response DTO")
    public void validMapItemRequestDTOToResponseDTO() {
        var appleEnum = GoodsEnum.valueOf(APPLE.toUpperCase());
        apples = ItemRequestDto.builder().itemName(APPLE).quantity(10).build();

        var result = itemMapper.toItemResponseDto(apples);
        assertEquals(appleEnum.name(), result.getItemName());
        assertEquals(appleEnum.getValue(), result.getItemPrice());
        assertEquals(apples.getQuantity(), result.getQuantity());
        assertEquals(appleEnum.getValue() * apples.getQuantity(), result.getTotal());
    }

}