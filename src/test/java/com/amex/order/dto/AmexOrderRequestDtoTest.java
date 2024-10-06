package com.amex.order.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
class AmexOrderRequestDtoTest {

    private static Validator validator;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("When Empty Items Then Exception")
    public void whenEmptyItemsThenException() {
        AmexOrderRequestDto amexOrderRequestDto = new AmexOrderRequestDto();

        Set<ConstraintViolation<AmexOrderRequestDto>> violations = validator.validate(amexOrderRequestDto);

        assertEquals(1, violations.size());
    }
  
}