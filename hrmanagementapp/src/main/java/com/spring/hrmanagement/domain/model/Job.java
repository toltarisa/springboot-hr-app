package com.spring.hrmanagement.domain.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "jobs")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@NoArgsConstructor
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    Timestamp lastApplicationDate;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "publisher_id", nullable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "job")
    private Set<Application> applications;

    public Job(String title, String description, Integer numberOfPeople, Timestamp lastApplicationDate) {
        this.title = title;
        this.description = description;
        this.numberOfPeople = numberOfPeople;
        this.lastApplicationDate = lastApplicationDate;
    }
}