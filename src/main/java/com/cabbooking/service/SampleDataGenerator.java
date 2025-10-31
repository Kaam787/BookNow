package com.cabbooking.service;

import com.cabbooking.entity.*;
import com.cabbooking.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Component
public class SampleDataGenerator implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SampleDataGenerator.class);
    private final Random random = new Random();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private CabRepository cabRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Value("${sample.data.enabled:false}")
    private boolean sampleDataEnabled;

    @Value("${sample.data.count:5}")
    private int sampleCount;

    @Override
    public void run(String... args) {
        if (!sampleDataEnabled) {
            log.info("Sample data generation is disabled. Set sample.data.enabled=true to enable it.");
            return;
        }

        long existingUsers = userRepository.count();
        if (existingUsers > 0) {
            log.info("Database already contains {} users — skipping sample data generation.", existingUsers);
            return;
        }

        log.info("Generating {} sample data sets...", sampleCount);

        try {
            String[] firstNames = {"Alex", "Jason", "Taylor", "Chris", "Max", "Sam", "Casey", "Drew"};
            String[] lastNames = {"Miller", "Lee", "Johnson", "Brown", "Garcia", "Martinez", "Davis", "Wilson"};
            String[] makes = {"Toyota", "Kia", "Ford", "Chevrolet", "Mercedes Benz"};
            String[] models = {"Corolla", "Civic", "Fusion", "Malibu", "Altima"};
            String[] colors = {"White", "Black", "Blue", "Red", "Gray"};
            Booking.BookingStatus[] statuses = Booking.BookingStatus.values();

            for (int i = 1; i <= sampleCount; i++) {
                String firstName = firstNames[random.nextInt(firstNames.length)];
                String lastName = lastNames[random.nextInt(lastNames.length)];

                User user = new User();
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(firstName.toLowerCase() + i + "@example.com");
                user.setPhoneNumber(String.format("90000%05d", 10000 + i));
                user.setPassword("testpass123");
                user.setRole(User.UserRole.CUSTOMER);
                user = userRepository.save(user);

                Driver driver = new Driver();
                driver.setUser(user);
                driver.setLicenseNumber("LIC" + (1000 + random.nextInt(9000)));
                driver.setLicenseExpiryDate("202" + (6 + random.nextInt(4)) + "-12-31");
                driver.setStatus(Driver.DriverStatus.ONLINE);
                driver.setVerificationStatus(Driver.VerificationStatus.VERIFIED);
                driver = driverRepository.save(driver);

                Cab cab = new Cab();
                cab.setDriver(driver);
                cab.setLicensePlate("CAB" + (100 + i) + (char)('A' + random.nextInt(3)));
                cab.setMake(makes[random.nextInt(makes.length)]);
                cab.setModel(models[random.nextInt(models.length)]);
                cab.setColor(colors[random.nextInt(colors.length)]);
                cab.setYear(2020 + random.nextInt(5));
                cab.setCabType(Cab.CabType.SEDAN);
                cab.setStatus(Cab.CabStatus.AVAILABLE);
                cab = cabRepository.save(cab);

                Booking booking = new Booking();
                booking.setBookingNumber("BKG" + System.currentTimeMillis() + i);
                booking.setUser(user);
                booking.setDriver(driver);
                booking.setCab(cab);
                booking.setPickupAddress((100 + i * 5) + " Main St");
                booking.setDropoffAddress((200 + i * 7) + " Park Ave");
                booking.setPickupLatitude(40.7128 + random.nextDouble() * 0.1);
                booking.setPickupLongitude(-74.0060 + random.nextDouble() * 0.1);
                booking.setDropoffLatitude(40.7589 + random.nextDouble() * 0.1);
                booking.setDropoffLongitude(-73.9851 + random.nextDouble() * 0.1);
                booking.setRequestedTime(LocalDateTime.now().minusDays(random.nextInt(7)));
                booking.setStatus(statuses[random.nextInt(statuses.length)]);
                booking.setRequestedCabType(Booking.CabType.SEDAN);

                int baseFare = 10 + random.nextInt(40);
                booking.setEstimatedFare(BigDecimal.valueOf(baseFare));
                booking.setActualFare(BigDecimal.valueOf(baseFare + random.nextInt(10) - 5));
                booking.setDistance(BigDecimal.valueOf(5 + random.nextInt(20)));
                booking.setEstimatedDuration(10 + random.nextInt(35));
                booking.setActualDuration(10 + random.nextInt(40));
                booking.setPaymentStatus(Booking.PaymentStatus.PENDING);
                booking.setPaymentMethod(random.nextBoolean() ? Booking.PaymentMethod.CASH : Booking.PaymentMethod.CARD);
                bookingRepository.save(booking);
            }

            log.info("Sample data generation completed: {} users, {} drivers, {} cabs, {} bookings",
                    userRepository.count(), driverRepository.count(),
                    cabRepository.count(), bookingRepository.count());

        } catch (Exception e) {
            log.error("Error during sample data generation", e);
        }
    }
}