package com.spring.hrmanagement.service.impl;

import com.spring.hrmanagement.domain.dto.SignupRequest;
import com.spring.hrmanagement.domain.model.ERole;
import com.spring.hrmanagement.domain.model.Role;
import com.spring.hrmanagement.domain.model.User;
import com.spring.hrmanagement.domain.repository.RoleRepository;
import com.spring.hrmanagement.domain.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl underTest;

    @Test
    @DisplayName("Should return User when Save user")
    void Should_ReturnUser_When_SaveUser() {

        //given
        SignupRequest signupRequest = new SignupRequest(
                "testName",
                "testSurname",
                "testUsername",
                "test@email.com",
                "testPassword",
                new HashSet<>(Collections.singletonList("applicant"))
        );

        Role role = new Role(1, ERole.ROLE_APPLICANT);

        //when
        when(roleRepository.findByName(ERole.ROLE_APPLICANT)).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("testPassword")).thenReturn("a0sd9080as9d0asd");
        when(userRepository.save(any(User.class))).thenReturn(new User());

        User savedUser = underTest.saveUser(signupRequest);

        //then
        assertThat(savedUser.getEmail()).isEqualTo(signupRequest.getEmail());
    }
}