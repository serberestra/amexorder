package com.amex.order.controller;

import com.amex.order.dto.AmexOrderRequestDto;
import com.amex.order.dto.AmexOrderResponseDto;
import com.amex.order.dto.ItemRequestDto;
import com.amex.order.dto.ItemResponseDto;
import com.amex.order.model.AppleItem;
import com.amex.order.model.OrangeItem;
import com.amex.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static com.amex.order.utils.OrderConstants.APPLE;
import static com.amex.order.utils.OrderConstants.ORANGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    ItemRequestDto appleRequest;
    ItemRequestDto orangeRequest;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        appleRequest = ItemRequestDto.builder().itemName(APPLE).quantity(10).build();
        orangeRequest = ItemRequestDto.builder().itemName(ORANGE).quantity(10).build();
    }

    @Test
    @DisplayName("When Valid Request Then Valid Response")
    public void whenValidRequestThenValidResponse() {
        var request = new AmexOrderRequestDto();
        request.setItem(appleRequest);
        request.setItem(orangeRequest);

        var apple = AppleItem.builder().build();
        apple.setQuantity(appleRequest.getQuantity());
        var appleResponse = ItemResponseDto.builder()
                .itemName(apple.name())
                .itemPrice(apple.price())
                .quantity(apple.quantity())
                .total(apple.total())
                .build();

        var orange = OrangeItem.builder().build();
        orange.setQuantity(orangeRequest.getQuantity());
        var orangeResponse = ItemResponseDto.builder()
                .itemName(orange.name())
                .itemPrice(orange.price())
                .quantity(orange.quantity())
                .total(orange.total())
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