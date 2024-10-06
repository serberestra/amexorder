package com.amex.order.controller;

import com.amex.order.dto.AmexOrderRequestDto;
import com.amex.order.dto.AmexOrderResponseDto;
import com.amex.order.dto.ItemRequestDto;
import com.amex.order.dto.ItemResponseDto;
import com.amex.order.model.GoodsEnum;
import com.amex.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.amex.order.OrderConstants.APPLE;
import static com.amex.order.OrderConstants.ORANGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    ItemRequestDto apples;
    ItemRequestDto oranges;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        apples = ItemRequestDto.builder().itemName(APPLE).quantity(10).build();
        oranges = ItemRequestDto.builder().itemName(ORANGE).quantity(10).build();
    }

    @Test
    @DisplayName("When Valid Request Then Valid Response")
    public void whenValidRequestThenValidResponse() {
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

        AmexOrderResponseDto amexOrderResponseDto = new AmexOrderResponseDto();
        amexOrderResponseDto.setItems(Set.of(appleResponse, orangeResponse));
        amexOrderResponseDto.setTotalOrder(appleResponse.getTotal() + orangeResponse.getTotal());

        when(orderService.summary(request)).thenReturn(amexOrderResponseDto);

        var response = orderController.createOrder(request);
        assertEquals(amexOrderResponseDto.getTotalOrder(), response.getTotalOrder());
        assertThat(response.getItems()).hasSize(2);
        assertThat(response.getItems()).contains(appleResponse);
        assertThat(response.getItems()).contains(orangeResponse);
    }

}