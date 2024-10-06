package com.amex.order.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemResponseDto {

    private String itemName;
    private Double itemPrice;
    private Integer quantity;
    private double total;

}
