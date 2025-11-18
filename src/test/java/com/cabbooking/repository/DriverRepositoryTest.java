package com.cabbooking.repository;

import com.cabbooking.entity.Driver;
import com.cabbooking.entity.User;
import com.cabbooking.testdata.DriverTestDataBuilder;
import com.cabbooking.testdata.UserTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DriverRepositoryTest {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private UserRepository userRepository;

    private double refLat;
    private double refLng;

    @BeforeEach
    void setUp() {
        // Reference point: Mexico City (CDMX)
        refLat = 19.4326;
        refLng = -99.1332;

        // Online + Verified exactly at reference point (ensures exact-match case)
        User u1 = userRepository.saveAndFlush(
                UserTestDataBuilder.aUser().withFirstName("Near").build());
        Driver d1 = DriverTestDataBuilder.aDriver()
                .withUser(u1)
                .atLocation(refLat, refLng)
                .build();
        driverRepository.save(d1);

        // Online + Verified within ~5 km
        User u2 = userRepository.saveAndFlush(
                UserTestDataBuilder.aUser().withFirstName("Mid").build());
        Driver d2 = DriverTestDataBuilder.aDriver()
                .withUser(u2)
                .atLocation(19.4000, -99.1500) // ~ 4 km
                .build();
        driverRepository.save(d2);

        // Online but Not Verified (should be excluded)
        User u3 = userRepository.saveAndFlush(
                UserTestDataBuilder.aUser().withFirstName("Unverified").build());
        Driver d3 = DriverTestDataBuilder.aDriver()
                .withUser(u3)
                .withVerification(Driver.VerificationStatus.PENDING)
                .atLocation(19.4330, -99.1330)
                .build();
        driverRepository.save(d3);

        // Offline but Verified (should be excluded)
        User u4 = userRepository.saveAndFlush(
                UserTestDataBuilder.aUser().withFirstName("Offline").build());
        Driver d4 = DriverTestDataBuilder.aDriver()
                .withUser(u4)
                .withStatus(Driver.DriverStatus.OFFLINE)
                .atLocation(19.4330, -99.1330)
                .build();
        driverRepository.save(d4);

        // Online + Verified but missing coords (excluded)
        User u5 = userRepository.saveAndFlush(
                UserTestDataBuilder.aUser().withFirstName("NoCoords").build());
        Driver d5 = DriverTestDataBuilder.aDriver()
                .withUser(u5)
                .atLocation(null, null)
                .build();
        driverRepository.save(d5);

        driverRepository.flush();
    }

    @Test
    @DisplayName("findNearbyAvailableDrivers returns drivers within radius, ordered by distance")
    void findNearbyAvailableDrivers_withinRadius() {
        List<Driver> drivers = driverRepository.findNearbyAvailableDrivers(refLat, refLng, 5.0);

        // Should return d1 and d2 only, ordered by distance (d1 closer first)
        assertThat(drivers).extracting(Driver::getUser)
                .extracting(u -> u.getFirstName())
                .containsExactly("Near", "Mid");
    }

    @Test
    @DisplayName("findNearbyAvailableDrivers with tiny radius returns drivers at exact location")
    void findNearbyAvailableDrivers_zeroRadius() {
        double EPS_KM = 0.001; // 1 meter, small positive radius to avoid ACOS 0-edge-case
        List<Driver> drivers = driverRepository.findNearbyAvailableDrivers(refLat, refLng, EPS_KM);
        assertThat(drivers)
                .hasSize(1)
                .extracting(d -> d.getUser().getFirstName())
                .containsExactly("Near");
    }

    @Test
    @DisplayName("findNearbyAvailableDrivers excludes non-online, non-verified, and null-coords")
    void findNearbyAvailableDrivers_exclusions() {
        List<Driver> drivers = driverRepository.findNearbyAvailableDrivers(refLat, refLng, 10.0);

        assertThat(drivers).allMatch(d ->
                d.getStatus() == Driver.DriverStatus.ONLINE &&
                        d.getVerificationStatus() == Driver.VerificationStatus.VERIFIED &&
                        d.getCurrentLatitude() != null && d.getCurrentLongitude() != null);
    }

    @Test
    @DisplayName("findNearbyAvailableDrivers returns empty when no drivers in radius")
    void findNearbyAvailableDrivers_noResults() {
        List<Driver> drivers = driverRepository.findNearbyAvailableDrivers(0.0, 0.0, 1.0);
        assertThat(drivers).isEmpty();
    }

    @Test
    @DisplayName("findNearbyAvailableDrivers handles negative or null radius gracefully (no results)")
    void findNearbyAvailableDrivers_invalidRadius() {
        assertThat(driverRepository.findNearbyAvailableDrivers(refLat, refLng, -1.0)).isEmpty();
        assertThat(driverRepository.findNearbyAvailableDrivers(refLat, refLng, null)).isEmpty();
    }
}
