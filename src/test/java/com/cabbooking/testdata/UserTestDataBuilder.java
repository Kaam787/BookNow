package com.cabbooking.testdata;

import com.cabbooking.entity.User;
import java.util.UUID;

public class UserTestDataBuilder {
    private String firstName = "John";
    private String lastName = "Doe";
    private String email = UUID.randomUUID() + "@example.com";
    private String phoneNumber = UUID.randomUUID().toString().substring(0, 10).replaceAll("[^0-9]", "0");// ✔ 10 dígitos
    private String password = "password123";
    private User.UserRole role = User.UserRole.CUSTOMER;
    private User.UserStatus status = User.UserStatus.ACTIVE;

    public static UserTestDataBuilder aUser() {
        return new UserTestDataBuilder();
    }

    public UserTestDataBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserTestDataBuilder withRole(User.UserRole role) {
        this.role = role;
        return this;
    }

    public UserTestDataBuilder withStatus(User.UserStatus status) {
        this.status = status;
        return this;
    }

    public UserTestDataBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserTestDataBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserTestDataBuilder withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public User build() {
        User u = new User();
        u.setFirstName(firstName);
        u.setLastName(lastName);
        u.setEmail(email);
        u.setPhoneNumber(phoneNumber);
        u.setPassword(password);
        u.setRole(role);
        u.setStatus(status);
        return u;
    }
}
