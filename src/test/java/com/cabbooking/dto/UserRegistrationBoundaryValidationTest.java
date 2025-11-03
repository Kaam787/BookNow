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
 * Boundary tests for size/length constraints on UserRegistrationDto.
 */
public class UserRegistrationBoundaryValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenFirstNameAtMinAndMaxBounds_thenNoViolation() {
        // min length 1
        UserRegistrationDto dtoMin = UserRegistrationDto.builder()
                .firstName("A")
                .lastName("Smith")
                .email("a.smith@example.com")
                .phoneNumber("1234567890")
                .password("password123")
                .role("CUSTOMER")
                .build();

        Set<ConstraintViolation<UserRegistrationDto>> violationsMin = validator.validate(dtoMin);
        assertTrue(violationsMin.stream().noneMatch(v -> v.getPropertyPath().toString().equals("firstName")),
                "Expected no firstName violation at min length");

        // max length 50
        String fifty = "A".repeat(50);
        UserRegistrationDto dtoMax = UserRegistrationDto.builder()
                .firstName(fifty)
                .lastName("Smith")
                .email("a.smith@example.com")
                .phoneNumber("1234567890")
                .password("password123")
                .role("CUSTOMER")
                .build();

        Set<ConstraintViolation<UserRegistrationDto>> violationsMax = validator.validate(dtoMax);
        assertTrue(violationsMax.stream().noneMatch(v -> v.getPropertyPath().toString().equals("firstName")),
                "Expected no firstName violation at max length");
    }

    @Test
    void whenFirstNameTooLong_thenViolation() {
        // 51 chars -> should violate
        String fiftyOne = "A".repeat(51);

        assertThrows(IllegalArgumentException.class, () ->
                UserRegistrationDto.builder()
                        .firstName(fiftyOne)
                        .lastName("Smith")
                        .email("a.smith@example.com")
                        .phoneNumber("1234567890")
                        .password("password123")
                        .role("CUSTOMER")
                        .build()
        );
    }


    @Test
    void whenPasswordTooShort_thenViolation() {
        assertThrows(IllegalArgumentException.class, () ->
                UserRegistrationDto.builder()
                        .firstName("Bob")
                        .lastName("Smith")
                        .email("bob.smith@example.com")
                        .phoneNumber("1234567890")
                        .password("short")
                        .role("CUSTOMER")
                        .build()
        );
    }
}
