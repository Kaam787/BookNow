package com.cabbooking.testdata;

import com.cabbooking.entity.Booking;
import com.cabbooking.entity.Driver;
import com.cabbooking.entity.User;

import java.time.LocalDateTime;

public class BookingTestDataBuilder {
    private String bookingNumber = "BK-" + System.nanoTime();
    private Booking.BookingStatus status = Booking.BookingStatus.REQUESTED;
    private LocalDateTime createdAt = LocalDateTime.now().minusMinutes(10);
    private LocalDateTime requestedTime = LocalDateTime.now().minusMinutes(5);
    private User user;
    private Driver driver;

    private String pickupAddress = "123 Main St";
    private Double pickupLatitude = 10.0;
    private Double pickupLongitude = 20.0;
    private String dropoffAddress = "456 Market Ave";
    private Double dropoffLatitude = 30.0;
    private Double dropoffLongitude = 40.0;
    private Booking.CabType requestedCabType = Booking.CabType.LUXURY;

    public static BookingTestDataBuilder aBooking() {
        return new BookingTestDataBuilder();
    }

    public BookingTestDataBuilder withStatus(Booking.BookingStatus status) {
        this.status = status;
        return this;
    }

    public BookingTestDataBuilder withRequestedTime(LocalDateTime rt) {
        this.requestedTime = rt;
        return this;
    }

    public BookingTestDataBuilder withCreatedAt(LocalDateTime ct) {
        this.createdAt = ct;
        return this;
    }

    public BookingTestDataBuilder withUser(User user) {
        this.user = user;
        return this;
    }

    public BookingTestDataBuilder withDriver(Driver driver) {
        this.driver = driver;
        return this;
    }

    public Booking build() {
        Booking b = new Booking();
        b.setBookingNumber(bookingNumber);
        b.setStatus(status);
        b.setCreatedAt(createdAt);
        b.setRequestedTime(requestedTime);

        b.setPickupAddress(pickupAddress);
        b.setPickupLatitude(pickupLatitude);
        b.setPickupLongitude(pickupLongitude);
        b.setDropoffAddress(dropoffAddress);
        b.setDropoffLatitude(dropoffLatitude);
        b.setDropoffLongitude(dropoffLongitude);
        b.setRequestedCabType(requestedCabType);

        b.setUser(user);
        b.setDriver(driver);
        return b;
    }
}
