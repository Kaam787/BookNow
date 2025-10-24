package com.cabbooking.util;

import java.math.BigDecimal;

/**
 * Global application constants for the Cab Booking System.
 * Contains reusable strings, numeric constants, and configuration values.
 */
public final class ApplicationConstants {

    private ApplicationConstants() {} // Prevent instantiation

    // ==========================================================
    // 🔹 Error Messages (Not Found)
    // ==========================================================
    public static final String BOOKING_NOT_FOUND = "Booking not found";
    public static final String DRIVER_NOT_FOUND = "Driver not found";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String CAB_NOT_FOUND = "Cab not found";

    // ==========================================================
    // 🔹 Business Rules & Validation Messages
    // ==========================================================
    public static final String USER_MUST_HAVE_DRIVER_ROLE = "User must have DRIVER role";
    public static final String DRIVER_IS_NOT_ONLINE = "Driver is not online";
    public static final String DRIVER_HAS_ACTIVE_BOOKING = "Driver already has an active booking";
    public static final String DRIVER_ALREADY_HAS_CAB_ASSIGNED = "Driver already has a cab assigned";
    public static final String DRIVER_DOES_NOT_HAVE_CAB = "Driver does not have a cab assigned";
    public static final String BOOKING_NOT_IN_REQUESTED_STATUS = "Booking is not in REQUESTED status";
    public static final String INVALID_STATUS_TRANSITION_MSG = "Invalid status transition from %s to %s";

    // ==========================================================
    // 🔹 Duplicate / Validation Errors
    // ==========================================================
    public static final String LICENSE_PLATE_ALREADY_EXISTS = "License plate already exists";
    public static final String LICENSE_NUMBER_ALREADY_EXISTS = "License number already exists";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists";
    public static final String PHONE_NUMBER_ALREADY_EXISTS = "Phone number already exists";

    // ==========================================================
    // 🔹 Data Validation Rules
    // ==========================================================
    public static final String PHONE_PATTERN = "^[0-9]{10}$";
    public static final int MAX_NAME_LENGTH = 50;
    public static final int MAX_EMAIL_LENGTH = 100;

    // ==========================================================
    // 🔹 Booking Configuration
    // ==========================================================
    public static final String BOOKING_NUMBER_PREFIX = "CAB";
    public static final int BOOKING_NUMBER_LENGTH = 8;
    public static final double DEFAULT_AVG_SPEED_KMH = 30.0;
    public static final int MINUTES_IN_HOUR = 60;
    public static final int SINGLE_RIDE_INCREMENT = 1;

    // ==========================================================
    // 🔹 Fare Configuration
    // ==========================================================
    public static final BigDecimal BASE_FARE = new BigDecimal("50.00");
    public static final BigDecimal HATCHBACK_FARE = new BigDecimal("10.00");
    public static final BigDecimal SEDAN_FARE = new BigDecimal("12.00");
    public static final BigDecimal SUV_FARE = new BigDecimal("15.00");
    public static final BigDecimal LUXURY_FARE = new BigDecimal("20.00");

    // ==========================================================
    // 🔹 Geo Calculations
    // ==========================================================
    public static final double EARTH_RADIUS = 6371; // Radius of Earth in kilometers

    // ==========================================================
    // 🔹 API Base Paths
    // ==========================================================
    public static final String API_LOCATIONS_BASE_PATH = "/api/locations";
    public static final String API_USERS_BASE_PATH = "/api/users";
    public static final String API_DRIVERS_BASE_PATH = "/api/drivers";
    public static final String API_CABS_BASE_PATH = "/api/cabs";
    public static final String API_BOOKINGS_BASE_PATH = "/api/bookings";

    // ==========================================================
    // 🔹 HTTP Status Codes
    // ==========================================================
    public static final int HTTP_OK = 200;
    public static final int HTTP_CREATED = 201;
    public static final int HTTP_BAD_REQUEST = 400;
    public static final int HTTP_UNAUTHORIZED = 401;
    public static final int HTTP_NOT_FOUND = 404;
    public static final int HTTP_INTERNAL_SERVER_ERROR = 500;

    // ==========================================================
    // 🔹 Authentication Messages
    // ==========================================================
    public static final String LOGIN_SUCCESS = "Login successful";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
}
