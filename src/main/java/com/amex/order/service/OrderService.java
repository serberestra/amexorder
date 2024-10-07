package com.amex.order.service;

import com.amex.order.dto.AmexOrderRequestDto;
import com.amex.order.dto.AmexOrderResponseDto;

import java.util.List;

public interface OrderService {
    AmexOrderResponseDto submit(AmexOrderRequestDto amexOrderRequestDto);
    AmexOrderResponseDto fetchOrderById(Long orderId);
    List<AmexOrderResponseDto> fetchAll();
}
