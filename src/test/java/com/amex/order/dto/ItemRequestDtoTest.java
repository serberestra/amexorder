package com.amex.order.dto;

import com.amex.order.utils.OrderConstants;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRequestDtoTest {

    private static Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("When Null Quantity Then Exception")
    public void whenNullQuantityThenException() {
        var itemRequest = ItemRequestDto.builder().itemName(OrderConstants.APPLE).build();

        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(itemRequest);

        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("When Quantity Less Than or Equals to ZERO Then Exception")
    public void whenQuantityLessThanOrEqualToZeroThenException() {
        var itemRequest = ItemRequestDto.builder().itemName(OrderConstants.APPLE).quantity(-10).build();

        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(itemRequest);

        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("When Empty Name Then Exception")
    public void whenEmptyNameThenException() {
        var itemRequest = ItemRequestDto.builder().itemName("").quantity(10).build();

        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(itemRequest);

        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("When Null Name Then Exception")
    public void whenNullNameThenException() {
        var itemRequest = ItemRequestDto.builder().quantity(10).build();

        Set<ConstraintViolation<ItemRequestDto>> violations = validator.validate(itemRequest);

        assertThat(violations.size()).isEqualTo(1);
    }

}