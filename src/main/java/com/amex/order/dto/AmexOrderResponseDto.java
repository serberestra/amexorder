package com.amex.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class AmexOrderResponseDto {

    private Long id;

    private Set<ItemResponseDto> items;

    private Double totalOrder;

}
