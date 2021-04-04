package com.spring.hrmanagement.controller;

import com.spring.hrmanagement.domain.dto.JwtResponse;
import com.spring.hrmanagement.domain.dto.LoginRequest;
import com.spring.hrmanagement.domain.dto.MessageResponse;
import com.spring.hrmanagement.domain.dto.SignupRequest;
import com.spring.hrmanagement.security.jwt.JwtUtils;
import com.spring.hrmanagement.security.services.UserDetailsImpl;
import com.spring.hrmanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@Validated
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthController (UserService userService, AuthenticationManager authenticationManager,
                           JwtUtils jwtUtils) {

        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping(value = "/signin")
    public ResponseEntity<?> authenticate(@RequestBody @Valid LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtResponse(
                        jwt,
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles
                ));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {

        String username = request.getUsername();
        String email = request.getEmail();

        Boolean usernameExists = userService.existsByUsername(username);
        Boolean emailExists = userService.existsByEmail(email);

        if(usernameExists)
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Username already exists"));

        if(emailExists)
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Email already exists"));

        Integer userId = userService.saveUser(request);

        return new ResponseEntity<>("User with id = " + userId + " registered succesfully", HttpStatus.CREATED);

    }
}
