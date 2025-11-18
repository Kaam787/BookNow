package com.cabbooking.testdata;

import com.cabbooking.entity.Driver;
import com.cabbooking.entity.User;

public class DriverTestDataBuilder {
    private Driver.DriverStatus status = Driver.DriverStatus.ONLINE;
    private Driver.VerificationStatus verificationStatus = Driver.VerificationStatus.VERIFIED;
    private Double currentLatitude = 19.4326;   // CDMX
    private Double currentLongitude = -99.1332; // CDMX
    private Double rating = 4.8;
    private String licenseNumber = "LIC-" + System.nanoTime();
    private String licenseExpiryDate = "2030-12-31";
    private User user;

    public static DriverTestDataBuilder aDriver() {
        return new DriverTestDataBuilder();
    }

    public DriverTestDataBuilder withStatus(Driver.DriverStatus status) {
        this.status = status;
        return this;
    }

    public DriverTestDataBuilder withVerification(Driver.VerificationStatus vs) {
        this.verificationStatus = vs;
        return this;
    }

    public DriverTestDataBuilder atLocation(Double lat, Double lng) {
        this.currentLatitude = lat;
        this.currentLongitude = lng;
        return this;
    }

    public DriverTestDataBuilder withRating(double rating) {
        this.rating = rating;
        return this;
    }

    public DriverTestDataBuilder withUser(User user) {
        this.user = user;
        return this;
    }

    public DriverTestDataBuilder withLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
        return this;
    }

    public DriverTestDataBuilder withLicenseExpiryDate(String date) {
        this.licenseExpiryDate = date;
        return this;
    }

    public Driver build() {
        Driver d = new Driver();
        d.setStatus(status);
        d.setVerificationStatus(verificationStatus);
        d.setCurrentLatitude(currentLatitude);
        d.setCurrentLongitude(currentLongitude);
        d.setRating(rating);
        d.setLicenseNumber(licenseNumber);
        d.setLicenseExpiryDate(licenseExpiryDate);
        d.setUser(user);
        return d;
    }
}
