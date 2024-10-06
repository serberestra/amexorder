package com.amex.order.service;

import com.amex.order.dto.AmexOrderRequestDto;
import com.amex.order.dto.ItemRequestDto;
import com.amex.order.dto.ItemResponseDto;
import com.amex.order.mapper.ItemMapper;
import com.amex.order.model.GoodsEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.amex.order.OrderConstants.APPLE;
import static com.amex.order.OrderConstants.ORANGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    ItemRequestDto apples;
    ItemRequestDto oranges;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        apples = ItemRequestDto.builder().itemName(APPLE).quantity(10).build();
        oranges = ItemRequestDto.builder().itemName(ORANGE).quantity(10).build();
    }

    @Test
    @DisplayName("Valid Order Service Summary")
    public void validOrderServiceSummary() {
        var request = new AmexOrderRequestDto();
        request.setItem(apples);
        request.setItem(oranges);

        var appleEnum = GoodsEnum.valueOf(APPLE.toUpperCase());
        var appleResponse = ItemResponseDto.builder()
                .itemName(apples.getItemName())
                .itemPrice(appleEnum.getValue())
                .quantity(apples.getQuantity())
                .total(appleEnum.getValue() * apples.getQuantity())
                .build();

        var orangeEnum = GoodsEnum.valueOf(ORANGE.toUpperCase());
        var orangeResponse = ItemResponseDto.builder()
                .itemName(oranges.getItemName())
                .itemPrice(orangeEnum.getValue())
                .quantity(oranges.getQuantity())
                .total(orangeEnum.getValue() * oranges.getQuantity())
                .build();

        when(itemMapper.toItemResponseDto(apples)).thenReturn(appleResponse);
        when(itemMapper.toItemResponseDto(oranges)).thenReturn(orangeResponse);

        var total = request.getItems().stream().map(itemMapper::toItemResponseDto).mapToDouble(ItemResponseDto::getTotal).sum();

        var response = orderService.summary(request);
        assertEquals(total, response.getTotalOrder());
        assertThat(response.getItems()).hasSize(2);
        assertThat(response.getItems()).anyMatch(i -> i.getTotal() == appleResponse.getTotal());
        assertThat(response.getItems()).anyMatch(i -> i.getTotal() == orangeResponse.getTotal());
    }

}