package com.amex.order.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AmexOrderResponseDtoTest {

    private static Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("When Empty Item Response Then Exception")
    public void whenNullQuantityThenException() {
        var response = new AmexOrderResponseDto();

        Set<ConstraintViolation<AmexOrderResponseDto>> violations = validator.validate(response);

        assertEquals(1, violations.size());
    }

}