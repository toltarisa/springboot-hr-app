package com.spring.hrmanagement.controller;

import com.spring.hrmanagement.domain.dto.JobListingDto;
import com.spring.hrmanagement.service.JobService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.security.Principal;

@RestController
@RequestMapping("/api/jobs")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobListingController {

    JobService jobService;

    public JobListingController (JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> saveJob(@Valid @RequestBody JobListingDto jobListingDto, Principal principal) {

        String username = principal.getName();
        Integer jobListingId = jobService.createJob(jobListingDto, username);

        if(jobListingId == null)
            return ResponseEntity.noContent().build();

        return new ResponseEntity<>("Job with id = " + jobListingId + " created", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> listAllJobs() {

        return new ResponseEntity<>(
            jobService.getAllJobs(),
            HttpStatus.OK
        );
    }

    @GetMapping(value = "/{jobListingId}")
    public ResponseEntity<?> listJobById(@Positive @PathVariable Integer jobListingId) {

        return new ResponseEntity<>(
            jobService.getJobListingById(jobListingId),
            HttpStatus.OK
        );
    }

    @DeleteMapping(value = "/{jobListingId}")
    public ResponseEntity<?> deleteJob(@Positive @PathVariable Integer jobListingId) {

        jobService.deleteJobListing(jobListingId);

        return new ResponseEntity<>(
                "Job with id = " + jobListingId + " removed successfully",
                HttpStatus.OK
        );
    }
}
