package com.amex.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AmexOrderRequestDto {

    @NotEmpty
    private Set<@Valid ItemRequestDto> items = new HashSet<>();

    public void setItem(@NotNull @Valid ItemRequestDto itemRequestDto) {
        items.add(itemRequestDto);
    }
}
