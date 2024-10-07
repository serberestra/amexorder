package com.amex.order.controller;

import com.amex.order.dto.AmexOrderRequestDto;
import com.amex.order.dto.AmexOrderResponseDto;
import com.amex.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public AmexOrderResponseDto createOrder(
           @Valid @RequestBody AmexOrderRequestDto amexOrderRequestDto
    ) {
        return orderService.submit(amexOrderRequestDto);
    }

    @GetMapping("/{id}")
    public AmexOrderResponseDto fetchOrderById(
            @PathVariable Long id
    ) {
        return orderService.fetchOrderById(id);
    }

    @GetMapping()
    public List<AmexOrderResponseDto> fetchAllOrders() {
        return orderService.fetchAll();
    }

}
