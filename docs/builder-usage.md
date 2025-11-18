# Builder Usage Documentation

## Overview

This document explains how the Builder pattern is implemented for DTOs in the **CabBooking** application and how validation is integrated automatically into the build process.

## DTOs Using Builders

* `UserRegistrationDto`
* `BookingRequestDto`

Both DTOs use Lombok’s `@Builder` annotation with a customized `build()` method that triggers validation using `ObjectValidator`.

---

## Example: UserRegistrationDto

### Implementation

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserRegistrationDto {

    @NotBlank @Size(min = 1, max = 50)
    private String firstName;

    @NotBlank @Size(min = 1, max = 50)
    private String lastName;

    @NotBlank @Email
    private String email;

    @NotBlank @Pattern(regexp = "\\d{10}")
    private String phoneNumber;

    @NotBlank @Size(min = 8)
    private String password;

    private String role = "CUSTOMER";

    public static class UserRegistrationDtoBuilder {
        public UserRegistrationDto build() {
            UserRegistrationDto dto = new UserRegistrationDto(
                    this.firstName, this.lastName, this.email,
                    this.phoneNumber, this.password, this.role);
            return ObjectValidator.validate(dto);
        }
    }
}
```

---

## How It Works

1. `@Builder` generates a static `builder()` method and a nested `UserRegistrationDtoBuilder` class.
2. You set fields using chained methods.
3. When you call `.build()`, the custom method invokes `ObjectValidator.validate(dto)`.
4. This ensures all field-level constraints (`@NotBlank`, `@Size`, etc.) are validated automatically.

---

## Example Usage

```java
UserRegistrationDto user = UserRegistrationDto.builder()
        .firstName("John")
        .lastName("Doe")
        .email("john.doe@example.com")
        .phoneNumber("9876543210")
        .password("password123")
        .role("CUSTOMER")
        .build();
```

If validation fails, an `IllegalArgumentException` is thrown with a descriptive message.

---

## Example Unit Test

```java
@Test
void whenInvalidEmail_thenExceptionThrown() {
    assertThrows(IllegalArgumentException.class, () ->
        UserRegistrationDto.builder()
            .firstName("Alice")
            .lastName("Smith")
            .email("not-an-email")
            .phoneNumber("1234567890")
            .password("password123")
            .role("CUSTOMER")
            .build()
    );
}
```

---

## Key Advantages

* Cleaner object creation with readable chaining.
* Centralized validation logic (no need to manually call validators).
* Reusable builder for updates (`toBuilder()`).
* Fail-fast object creation — invalid objects are never created.

---

## Related Classes

* `ObjectValidator.java` — Utility class handling bean validation.
