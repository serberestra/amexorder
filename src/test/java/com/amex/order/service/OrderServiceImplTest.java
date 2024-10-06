package com.amex.order.service;

import com.amex.order.dto.AmexOrderRequestDto;
import com.amex.order.dto.ItemRequestDto;
import com.amex.order.dto.ItemResponseDto;
import com.amex.order.mapper.ItemMapper;
import com.amex.order.model.AppleItem;
import com.amex.order.model.OrangeItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.amex.order.utils.OrderConstants.APPLE;
import static com.amex.order.utils.OrderConstants.ORANGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    ItemRequestDto appleRequest;
    ItemRequestDto orangeRequest;

    @Mock
    private ItemMapper itemMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        appleRequest = ItemRequestDto.builder().itemName(APPLE).quantity(10).build();
        orangeRequest = ItemRequestDto.builder().itemName(ORANGE).quantity(10).build();
    }

    @Test
    @DisplayName("Valid Order Service Summary")
    public void validOrderServiceSummary() {
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

        when(itemMapper.toItemResponseDto(appleRequest)).thenReturn(appleResponse);
        when(itemMapper.toItemResponseDto(orangeRequest)).thenReturn(orangeResponse);

        var total = request.getItems().stream().map(itemMapper::toItemResponseDto).mapToDouble(ItemResponseDto::getTotal).sum();

        var response = orderService.summary(request);
        assertEquals(total, response.getTotalOrder());
        assertThat(response.getItems()).hasSize(2);
        assertThat(response.getItems()).anyMatch(i -> i.getTotal() == appleResponse.getTotal());
        assertThat(response.getItems()).anyMatch(i -> i.getTotal() == orangeResponse.getTotal());
    }

}