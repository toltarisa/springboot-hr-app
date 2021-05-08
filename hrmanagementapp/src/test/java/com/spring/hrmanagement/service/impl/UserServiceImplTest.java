package com.spring.hrmanagement.service.impl;

import com.spring.hrmanagement.domain.dto.SignupRequest;
import com.spring.hrmanagement.domain.model.User;
import com.spring.hrmanagement.domain.repository.RoleRepository;
import com.spring.hrmanagement.domain.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;


    private UserServiceImpl underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
    }

    @Test
    void saveUser() {

        //given
        User user = new User(
          "isa",
          "toltar",
          "isatoltar",
          "isat@test.com",
          "isatoltar45"
        );

        //when
        underTest.saveUser(
                new SignupRequest(
                        "isa",
                        "toltar",
                        "isatoltar",
                        "isat@test.com",
                        "isatoltar45",
                        new HashSet<>(Collections.singletonList("applicant"))
                )
        );

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertThat(capturedUser).isEqualTo(user);

    }
}