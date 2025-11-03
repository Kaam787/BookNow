package com.cabbooking.dto;

import com.cabbooking.util.ObjectValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;

import static com.cabbooking.util.ApplicationConstants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BookingRequestDto {

    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be a positive number")
    private Long userId;

    @NotBlank(message = "Pickup address is required")
    @Size(max = MAX_ADDRESS_LENGTH, message = "Pickup address must be " + MAX_ADDRESS_LENGTH + " characters or fewer")
    private String pickupAddress;

    @NotNull(message = "Pickup latitude is required")
    @DecimalMin(value = "" + MIN_LATITUDE, message = "Pickup latitude must be >= " + MIN_LATITUDE)
    @DecimalMax(value = "" + MAX_LATITUDE, message = "Pickup latitude must be <= " + MAX_LATITUDE)
    private Double pickupLatitude;

    @NotNull(message = "Pickup longitude is required")
    @DecimalMin(value = "" + MIN_LONGITUDE, message = "Pickup longitude must be >= " + MIN_LONGITUDE)
    @DecimalMax(value = "" + MAX_LONGITUDE, message = "Pickup longitude must be <= " + MAX_LONGITUDE)
    private Double pickupLongitude;

    @NotBlank(message = "Dropoff address is required")
    @Size(max = MAX_ADDRESS_LENGTH, message = "Dropoff address must be " + MAX_ADDRESS_LENGTH + " characters or fewer")
    private String dropoffAddress;

    @NotNull(message = "Dropoff latitude is required")
    @DecimalMin(value = "" + MIN_LATITUDE, message = "Dropoff latitude must be >= " + MIN_LATITUDE)
    @DecimalMax(value = "" + MAX_LATITUDE, message = "Dropoff latitude must be <= " + MAX_LATITUDE)
    private Double dropoffLatitude;

    @NotNull(message = "Dropoff longitude is required")
    @DecimalMin(value = "" + MIN_LONGITUDE, message = "Dropoff longitude must be >= " + MIN_LONGITUDE)
    @DecimalMax(value = "" + MAX_LONGITUDE, message = "Dropoff longitude must be <= " + MAX_LONGITUDE)
    private Double dropoffLongitude;

    @NotBlank(message = "Requested cab type is required")
    @Size(max = MAX_CAB_TYPE_LENGTH, message = "Requested cab type must be " + MAX_CAB_TYPE_LENGTH + " characters or fewer")
    private String requestedCabType;

    @Size(max = MAX_SPECIAL_INSTRUCTIONS_LENGTH, message = "Special instructions must be " + MAX_SPECIAL_INSTRUCTIONS_LENGTH + " characters or fewer")
    private String specialInstructions;

    @Size(max = MAX_PAYMENT_METHOD_LENGTH, message = "Payment method must be " + MAX_PAYMENT_METHOD_LENGTH + " characters or fewer")
    private String paymentMethod;

    public static class BookingRequestDtoBuilder {
        public BookingRequestDto build() {
            BookingRequestDto dto = new BookingRequestDto(
                    this.userId,
                    this.pickupAddress,
                    this.pickupLatitude,
                    this.pickupLongitude,
                    this.dropoffAddress,
                    this.dropoffLatitude,
                    this.dropoffLongitude,
                    this.requestedCabType,
                    this.specialInstructions,
                    this.paymentMethod
            );
            return ObjectValidator.validate(dto);
        }
    }
}
