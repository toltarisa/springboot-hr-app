package com.spring.hrmanagement.service;

import com.spring.hrmanagement.domain.dto.JobListingDto;
import com.spring.hrmanagement.domain.model.Job;

import java.util.List;

public interface JobService {

    Integer createJob(JobListingDto dto, String username);

    List<JobListingDto> getAllJobs();

    JobListingDto getJobListingById(Integer jobListingId);

    Job getJobById(Integer jobListingId);

    void deleteJobListing(Integer jobListingId);
}
