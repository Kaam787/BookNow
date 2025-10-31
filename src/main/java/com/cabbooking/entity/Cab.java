package com.cabbooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "cabs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "driver_id", nullable = false)
    @jakarta.validation.constraints.NotNull(message = "Driver is required")
    @jakarta.validation.Valid
    private Driver driver;

    @Column(unique = true, nullable = false)
    @jakarta.validation.constraints.NotBlank(message = "License plate is required")
    private String licensePlate;

    @Column(nullable = false)
    @jakarta.validation.constraints.NotBlank(message = "Make is required")
    private String make;

    @Column(nullable = false)
    @jakarta.validation.constraints.NotBlank(message = "Model is required")
    private String model;

    @Column(nullable = false)
    @jakarta.validation.constraints.NotBlank(message = "Color is required")
    private String color;

    @Column(name = "manufacture_year", nullable = false)
    @jakarta.validation.constraints.NotNull(message = "Year is required")
    private Integer year;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CabType cabType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CabStatus status = CabStatus.AVAILABLE;

    private Integer seatingCapacity;

    @OneToMany(mappedBy = "cab", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "Cab{" +
                "id=" + id +
                ", driverId=" + (driver != null ? driver.getId() : "null") +
                ", licensePlate='" + licensePlate + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", cabType=" + cabType +
                ", status=" + status +
                ", seatingCapacity=" + seatingCapacity +
                '}';
    }

    public enum CabType {
        SEDAN, HATCHBACK, SUV, LUXURY
    }

    public enum CabStatus {
        AVAILABLE, BUSY, MAINTENANCE, INACTIVE
    }
}