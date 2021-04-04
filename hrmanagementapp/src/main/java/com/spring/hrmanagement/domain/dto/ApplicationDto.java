package com.spring.hrmanagement.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ApplicationDto {

    @NotBlank
    @Size(max = 100)
    String name;

    @NotBlank
    @Size(max = 100)
    String email;

    @NotBlank
    @Size(max = 16)
    String phone;

    @NotBlank
    @Size(max = 150)
    String address;

    @NotBlank
    @Size(max = 200)
    String thoughts;

    @Size(max = 200)
    String fileUrl;
}
