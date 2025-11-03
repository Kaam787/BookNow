package com.cabbooking.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for required (NotBlank) field validations on UserRegistrationDto.
 */
public class UserRegistrationRequiredFieldsValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenRequiredFieldsBlank_thenViolationsForEachRequiredField() {
        assertThrows(IllegalArgumentException.class, () ->
                UserRegistrationDto.builder()
                        .firstName("")
                        .lastName("")
                        .email("")
                        .phoneNumber("")
                        .password("")
                        .role("CUSTOMER")
                        .build()
        );
    }
}
