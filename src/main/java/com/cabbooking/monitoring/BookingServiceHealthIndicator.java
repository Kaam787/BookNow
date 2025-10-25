package com.cabbooking.monitoring;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class BookingServiceHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        boolean serviceUp = checkBookingService();
        if (serviceUp) {
            return Health.up().withDetail("bookingService", "Running smoothly").build();
        } else {
            return Health.down().withDetail("bookingService", "Unavailable").build();
        }
    }

    private boolean checkBookingService() {
        // Add your custom logic here — e.g., check an internal service or dependency
        return true;
    }
}
