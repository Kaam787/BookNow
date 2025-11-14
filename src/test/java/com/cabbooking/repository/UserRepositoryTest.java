package com.cabbooking.repository;

import com.cabbooking.entity.User;
import com.cabbooking.testdata.UserTestDataBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("findByEmail should return user when email exists (exact match)")
    void findByEmail_returnsUser_whenEmailExists() {
        // arrange
        String email = "jessica@example.com";
        User user = UserTestDataBuilder.aUser()
                .withEmail(email)
                .withFirstName("Jessica")
                .withLastName("M.")
                .build();
        userRepository.saveAndFlush(user);

        // act
        Optional<User> found = userRepository.findByEmail(email);

        // assert
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo(email);
        assertThat(found.get().getFirstName()).isEqualTo("Jessica");
    }

    @Test
    @DisplayName("findByEmail should be case-sensitive by default (no match on different case)")
    void findByEmail_isCaseSensitive_default() {
        // arrange
        String email = "lowercase@example.com";
        userRepository.saveAndFlush(UserTestDataBuilder.aUser().withEmail(email).build());

        // act
        Optional<User> found = userRepository.findByEmail(email.toUpperCase());

        // assert
        assertThat(found).isNotPresent();
    }

    @Test
    @DisplayName("findByEmail should return empty when email not found")
    void findByEmail_returnsEmpty_whenNotFound() {
        // act
        Optional<User> found = userRepository.findByEmail("notfound@example.com");

        // assert
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("findByEmail should handle null safely (returns empty)")
    void findByEmail_handlesNull() {
        // act
        Optional<User> found = userRepository.findByEmail(null);

        // assert
        assertThat(found).isEmpty();
    }
}
