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
@Table(name = "drivers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @jakarta.validation.constraints.NotNull(message = "User is required")
    @jakarta.validation.Valid
    private User user;

    @Column(unique = true, nullable = false)
    @jakarta.validation.constraints.NotBlank(message = "License number is required")
    private String licenseNumber;

    @Column(nullable = false)
    @jakarta.validation.constraints.NotBlank(message = "License expiry date is required")
    private String licenseExpiryDate;

    private Double rating = 0.0;

    private Integer totalRides = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DriverStatus status = DriverStatus.OFFLINE;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationStatus verificationStatus = VerificationStatus.PENDING;

    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL)
    private Cab cab;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings;

    // Current location
    private Double currentLatitude;
    private Double currentLongitude;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", userId=" + (user != null ? user.getId() : "null") +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", rating=" + rating +
                ", totalRides=" + totalRides +
                ", status=" + status +
                ", verificationStatus=" + verificationStatus +
                ", cabId=" + (cab != null ? cab.getId() : "null") +
                ", currentLatitude=" + currentLatitude +
                ", currentLongitude=" + currentLongitude +
                '}';
    }

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum DriverStatus {
        ONLINE, OFFLINE, BUSY
    }

    public enum VerificationStatus {
        PENDING, VERIFIED, REJECTED
    }
}