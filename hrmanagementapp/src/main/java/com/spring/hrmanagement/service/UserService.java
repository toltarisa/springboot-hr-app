package com.spring.hrmanagement.service;

import com.spring.hrmanagement.domain.dto.SignupRequest;
import com.spring.hrmanagement.domain.model.User;

public interface UserService {

    User findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Integer saveUser(SignupRequest request);
}
