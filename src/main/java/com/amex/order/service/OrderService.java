package com.amex.order.service;

import com.amex.order.dto.AmexOrderRequestDto;
import com.amex.order.dto.AmexOrderResponseDto;

public interface OrderService {
    AmexOrderResponseDto summary(AmexOrderRequestDto amexOrderRequestDto);
}
