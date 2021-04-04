package com.spring.hrmanagement.exception.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CustomFieldError {

    private String field;

    private String message;
}
