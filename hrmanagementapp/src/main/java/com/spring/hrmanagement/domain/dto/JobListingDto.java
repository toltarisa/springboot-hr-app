package com.spring.hrmanagement.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobListingDto {

    Integer id;

    @NotBlank
    @Size(max = 100)
    String title;

    @NotBlank
    @Size(max = 500)
    String description;

    @Positive
    @Column(name = "number_of_people")
    Integer numberOfPeople;

    @Column(name = "last_application_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    Date lastApplicationDate;


}
