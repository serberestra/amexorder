package com.amex.order.mapper;

import com.amex.order.dto.ItemRequestDto;
import com.amex.order.exception.NotFoundException;
import com.amex.order.model.AppleItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.amex.order.utils.OrderConstants.APPLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemMapperTest {

    ItemRequestDto appleRequest;

    private ItemMapper itemMapper;

    @BeforeEach
    void setUp() {
        itemMapper = new ItemMapper();
        appleRequest = ItemRequestDto.builder().itemName(APPLE).quantity(10).build();
    }

    @Test
    @DisplayName("When Valid Map Item Request DTO Then Valid Response DTO")
    public void validMapItemRequestDTOToResponseDTO() {
        var apple = AppleItem.builder().build();
        apple.setQuantity(appleRequest.getQuantity());

        var result = itemMapper.toItemResponseDto(appleRequest);

        assertEquals(apple.name(), result.getItemName());
        assertEquals(apple.price(), result.getItemPrice());
        assertEquals(appleRequest.getQuantity(), result.getQuantity());
        assertEquals(apple.total(), result.getTotal());
    }

    @Test
    @DisplayName("When Name Item Not Found Then Exception")
    public void whenNameItemNotFound() {
        appleRequest.setItemName("apples");
        var apple = AppleItem.builder().build();
        apple.setQuantity(appleRequest.getQuantity());

        Exception exception = assertThrows(
                NotFoundException.class,
                () -> itemMapper.toItemResponseDto(appleRequest)
        );

        assertNotNull(exception);
        assertEquals("Item 'apples' not found",exception.getMessage());
    }

}