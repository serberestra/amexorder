package com.amex.order.service;

import com.amex.order.dto.AmexOrderRequestDto;
import com.amex.order.dto.AmexOrderResponseDto;
import com.amex.order.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final ItemMapper itemMapper;

    public AmexOrderResponseDto summary(AmexOrderRequestDto amexOrderRequestDto) {
        AmexOrderResponseDto amexOrderResponseDto = new AmexOrderResponseDto();
        amexOrderResponseDto.setItems(
                amexOrderRequestDto.getItems()
                        .stream()
                        .map(itemMapper::toItemResponseDto)
                        .collect(Collectors.toSet())
        );
        amexOrderResponseDto.totalOrder();
        return amexOrderResponseDto;
    }

}
