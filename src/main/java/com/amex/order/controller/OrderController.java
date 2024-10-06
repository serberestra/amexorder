package com.amex.order.controller;

import com.amex.order.dto.AmexOrderRequestDto;
import com.amex.order.dto.AmexOrderResponseDto;
import com.amex.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public AmexOrderResponseDto createOrder(
           @Valid @RequestBody AmexOrderRequestDto amexOrderRequestDto
    ) {
        return orderService.summary(amexOrderRequestDto);
    }

}
