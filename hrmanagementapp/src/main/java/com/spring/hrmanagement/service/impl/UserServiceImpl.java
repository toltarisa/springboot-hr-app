package com.spring.hrmanagement.service.impl;

import com.spring.hrmanagement.domain.dto.SignupRequest;
import com.spring.hrmanagement.domain.model.ERole;
import com.spring.hrmanagement.domain.model.Role;
import com.spring.hrmanagement.domain.model.User;
import com.spring.hrmanagement.domain.repository.RoleRepository;
import com.spring.hrmanagement.domain.repository.UserRepository;
import com.spring.hrmanagement.exception.DbOperationException;
import com.spring.hrmanagement.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl (UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(() ->  new DbOperationException("Unable to find user with username = " + username));
    }

    @Override
    public Boolean existsByUsername(String username) {
        try {
            return userRepository.existsByUsername(username);
        } catch(Exception ex) {
            logger.error("Db exception occured when checking username = {}"+username, ex.getMessage());
            throw new DbOperationException("Resource with username = " + username + "is not exists");
        }
    }

    @Override
    public Boolean existsByEmail(String email) {
        try {
            return userRepository.existsByUsername(email);
        } catch(Exception ex) {
            logger.error("Db exception occured when checking email = {}"+email, ex.getMessage());
            throw new DbOperationException("Resource with email = " + email + "is not exists");
        }
    }

    @Override
    public Integer saveUser(SignupRequest request) {

        String name = request.getName();
        String surname = request.getSurname();
        String username = request.getUsername();
        String email = request.getEmail();
        String password = passwordEncoder.encode(request.getPassword());

        User user = new User(name,surname,username,email,password);

        Set<String> strRoles = request.getRole();
        Set<Role> roles = getRoles(strRoles);

        user.setRoles(roles);

        try {
            userRepository.save(user);
        } catch(Exception e) {
            throw new DbOperationException("Error occured when trying to save user to db!");
        }

        return user.getId();
    }

    private Set<Role> getRoles(Set<String> strRoles) {

        Set<Role> roles = new HashSet<>();

        if(strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_APPLICANT)
                    .orElseThrow(() -> new DbOperationException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if(role.equals("applicant")) {
                    Role adminRole = roleRepository.findByName(ERole.ROLE_APPLICANT)
                            .orElseThrow(() -> new DbOperationException("Error: Role is not found") );
                    roles.add(adminRole);
                }

                if(role.equals("manager")) {
                    Role userRole = roleRepository.findByName(ERole.ROLE_MANAGER)
                            .orElseThrow(() -> new DbOperationException("Error: Role is not found") );
                    roles.add(userRole);
                }
            });
        }

        return roles;
    }
}
