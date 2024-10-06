package com.amex.order.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class AmexOrderResponseDto {

    @NotEmpty
    private Set<ItemResponseDto> items;
    private Double totalOrder;

    public void totalOrder() {
        totalOrder = items.stream().mapToDouble(ItemResponseDto::getTotal).sum();
    }
}
