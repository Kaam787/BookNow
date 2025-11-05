-- Flyway Migration: Initial Database Schema

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(10) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'CUSTOMER',
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS drivers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    license_number VARCHAR(255) NOT NULL UNIQUE,
    license_expiry_date VARCHAR(255) NOT NULL,
    rating DOUBLE DEFAULT 0.0,
    total_rides INT DEFAULT 0,
    status VARCHAR(20) NOT NULL DEFAULT 'OFFLINE',
    verification_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    current_latitude DOUBLE,
    current_longitude DOUBLE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_driver_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS cabs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    driver_id BIGINT NOT NULL,
    license_plate VARCHAR(255) NOT NULL UNIQUE,
    make VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL,
    color VARCHAR(255) NOT NULL,
    manufacture_year INT NOT NULL,
    cab_type VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',
    seating_capacity INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_cab_driver FOREIGN KEY (driver_id) REFERENCES drivers(id) ON DELETE CASCADE
);

-- Bookings table
CREATE TABLE IF NOT EXISTS bookings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_number VARCHAR(255) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    driver_id BIGINT,
    cab_id BIGINT,
    pickup_address VARCHAR(500) NOT NULL,
    pickup_latitude DOUBLE NOT NULL,
    pickup_longitude DOUBLE NOT NULL,
    dropoff_address VARCHAR(500) NOT NULL,
    dropoff_latitude DOUBLE NOT NULL,
    dropoff_longitude DOUBLE NOT NULL,
    requested_time TIMESTAMP NOT NULL,
    accepted_time TIMESTAMP,
    start_time TIMESTAMP,
    end_time TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'REQUESTED',
    requested_cab_type VARCHAR(20) NOT NULL,
    estimated_fare DECIMAL(10, 2),
    actual_fare DECIMAL(10, 2),
    distance DECIMAL(10, 2),
    estimated_duration INT,
    actual_duration INT,
    payment_status VARCHAR(20) DEFAULT 'PENDING',
    payment_method VARCHAR(20),
    special_instructions VARCHAR(1000),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_booking_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_booking_driver FOREIGN KEY (driver_id) REFERENCES drivers(id) ON DELETE SET NULL,
    CONSTRAINT fk_booking_cab FOREIGN KEY (cab_id) REFERENCES cabs(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS location_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    driver_id BIGINT NOT NULL,
    booking_id BIGINT,
    latitude DOUBLE NOT NULL,
    longitude DOUBLE NOT NULL,
    speed DOUBLE NOT NULL,
    heading DOUBLE NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_location_log_driver FOREIGN KEY (driver_id) REFERENCES drivers(id) ON DELETE CASCADE,
    CONSTRAINT fk_location_log_booking FOREIGN KEY (booking_id) REFERENCES bookings(id) ON DELETE SET NULL
);