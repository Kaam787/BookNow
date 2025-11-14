package com.cabbooking.repository;

import com.cabbooking.entity.Booking;
import com.cabbooking.entity.Driver;
import com.cabbooking.entity.User;
import com.cabbooking.testdata.BookingTestDataBuilder;
import com.cabbooking.testdata.DriverTestDataBuilder;
import com.cabbooking.testdata.UserTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DriverRepository driverRepository;

    private User user;
    private Driver driver;

    @BeforeEach
    void setUp() {
        user = userRepository.saveAndFlush(
                UserTestDataBuilder.aUser().withFirstName("Jess").build());
        driver = driverRepository.saveAndFlush(
                DriverTestDataBuilder.aDriver().withUser(user).build());

        LocalDateTime now = LocalDateTime.now();

        // REQUESTED at or before now -> included
        bookingRepository.save(BookingTestDataBuilder.aBooking()
                .withUser(user).withDriver(driver)
                .withStatus(Booking.BookingStatus.REQUESTED)
                .withRequestedTime(now.minusMinutes(1))
                .build());

        // ACCEPTED at or before now -> included
        bookingRepository.save(BookingTestDataBuilder.aBooking()
                .withUser(user).withDriver(driver)
                .withStatus(Booking.BookingStatus.ACCEPTED)
                .withRequestedTime(now)
                .build());

        // REQUESTED after now -> excluded
        bookingRepository.save(BookingTestDataBuilder.aBooking()
                .withUser(user).withDriver(driver)
                .withStatus(Booking.BookingStatus.REQUESTED)
                .withRequestedTime(now.plusMinutes(5))
                .build());

        // Other statuses -> excluded
        bookingRepository.save(BookingTestDataBuilder.aBooking()
                .withUser(user).withDriver(driver)
                .withStatus(Booking.BookingStatus.CANCELLED)
                .withRequestedTime(now.minusMinutes(2))
                .build());

        bookingRepository.flush();
    }

    @Test
    @DisplayName("findPendingBookings returns REQUESTED and ACCEPTED with requestedTime <= now ordered ascending")
    void findPendingBookings_includesAndOrdersCorrectly() {
        LocalDateTime now = LocalDateTime.now();
        List<Booking> pending = bookingRepository.findPendingBookings(now);

        assertThat(pending).hasSize(2);
        assertThat(pending.get(0).getStatus()).isIn(
                Booking.BookingStatus.REQUESTED, Booking.BookingStatus.ACCEPTED);
        assertThat(pending.get(1).getStatus()).isIn(
                Booking.BookingStatus.REQUESTED, Booking.BookingStatus.ACCEPTED);

        // Ordered by requestedTime ASC
        assertThat(pending.get(0).getRequestedTime())
                .isBeforeOrEqualTo(pending.get(1).getRequestedTime());
        assertThat(pending).allMatch(b -> !b.getRequestedTime().isAfter(now));
    }

    @Test
    @DisplayName("findPendingBookings returns empty list when nothing matches")
    void findPendingBookings_emptyWhenNoMatches() {
        LocalDateTime past = LocalDateTime.now().minusHours(1);
        // All created are later than 'past', but requestedTime condition uses past; ensure method logic respects <= currentTime
        List<Booking> pendingPast = bookingRepository.findPendingBookings(past);
        assertThat(pendingPast).isEmpty();
    }

    @Test
    @DisplayName("findPendingBookings handles null currentTime gracefully (empty)")
    void findPendingBookings_nullCurrentTime() {
        List<Booking> pendingNull = bookingRepository.findPendingBookings(null);
        assertThat(pendingNull).isEmpty();
    }
}
