package com.amex.order.dto;

import com.amex.order.model.GoodsEnum;
import com.amex.order.utils.ValueOfEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class ItemRequestDto {

    @ValueOfEnum(enumClass = GoodsEnum.class)
    private String itemName;

    @NotNull
    private Integer quantity;

}
