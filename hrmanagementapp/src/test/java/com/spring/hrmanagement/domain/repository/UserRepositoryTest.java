package com.spring.hrmanagement.domain.repository;

import com.spring.hrmanagement.domain.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void ItShouldCheckIfUserExistsByUsername() {

        //given
        String username = "isatoltar";
        User user = new User("isa", "toltar", username, "isa@test.com", "isatoltar");
        underTest.save(user);

        //when
        Boolean exists = underTest.existsByUsername(username);

        //then
        assertThat(exists).isTrue();
    }

    @Test
    void ItShouldCheckIfUserExistsByEmail() {

        //given
        String email = "isa@test.com";
        User user = new User("isa", "toltar", "isatoltar", email, "isatoltar");
        underTest.save(user);

        //when
        Boolean exists = underTest.existsByEmail(email);

        //then
        assertThat(exists).isTrue();
    }
}