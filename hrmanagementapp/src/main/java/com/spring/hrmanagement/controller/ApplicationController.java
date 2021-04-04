package com.spring.hrmanagement.controller;

import com.spring.hrmanagement.service.ApplicationService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/jobs")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationController {

    ApplicationService applicationService;

    public ApplicationController (ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping(value = "/{jobId}/applications")
    public ResponseEntity<?> createApplication(@RequestPart(value = "request") String request,
                                               @PathVariable @Positive Integer jobId,
                                               @RequestPart(value = "file") MultipartFile file) {

        Integer applicationId = applicationService.createApplication(request, jobId, file);

        return new ResponseEntity<>(
                "Application with id = " + applicationId + " created successfully",
                HttpStatus.CREATED
        );
    }

    @GetMapping(value = "/{jobId}/applications")
    public ResponseEntity<?> getAllApplications(@PathVariable @Positive Integer jobId) {

        return new ResponseEntity<>(
                applicationService.listAllApplications(jobId),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/{jobId}/applications/{applicationId}")
    public ResponseEntity<?> getOneApplication(@PathVariable @Positive Integer jobId,
                                               @PathVariable @Positive Integer applicationId) {

        return new ResponseEntity<>(
                applicationService.getOneApplicationById(applicationId, jobId),
                HttpStatus.OK
        );
    }
}
