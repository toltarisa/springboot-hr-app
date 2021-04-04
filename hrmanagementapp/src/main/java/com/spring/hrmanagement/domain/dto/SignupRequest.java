package com.spring.hrmanagement.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {


    @NotBlank
    @Size(max = 30)
    String name;

    @NotBlank
    @Size(max = 30)
    String surname;

    @NotBlank
    @Size(max = 30)
    String username;

    @NotBlank
    @Size(max = 50)
    @Email
    String email;

    @NotBlank
    @Size(max = 120)
    String password;

    @NotEmpty
    private Set<String> role;
}
