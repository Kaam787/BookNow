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
 * Tests for email validation on UserRegistrationDto.
 */
public class UserRegistrationEmailValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenValidEmail_thenNoEmailViolation() {
        UserRegistrationDto dto = UserRegistrationDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("1234567890")
                .password("password123")
                .role("CUSTOMER")
                .build();

        Set<ConstraintViolation<UserRegistrationDto>> violations = validator.validate(dto);

        assertTrue(
                violations.stream().noneMatch(v -> v.getPropertyPath().toString().equals("email")),
                "Expected no email violations for a valid email"
        );
    }

    @Test
    void whenInvalidEmail_thenEmailViolation() {
        assertThrows(IllegalArgumentException.class, () ->
                UserRegistrationDto.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .email("not-an-email")
                        .phoneNumber("1234567890")
                        .password("password123")
                        .role("CUSTOMER")
                        .build()
        );
    }
}
