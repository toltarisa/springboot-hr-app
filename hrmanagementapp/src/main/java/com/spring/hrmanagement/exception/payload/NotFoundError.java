package com.spring.hrmanagement.exception.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class NotFoundError {

    private Date timestamp;
    private String message;
    private String details;
}
