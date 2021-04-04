package com.spring.hrmanagement.service.impl;

import com.spring.hrmanagement.domain.dto.JobListingDto;
import com.spring.hrmanagement.domain.model.Job;
import com.spring.hrmanagement.domain.model.User;
import com.spring.hrmanagement.domain.repository.JobRepository;
import com.spring.hrmanagement.exception.ApiRequestException;
import com.spring.hrmanagement.exception.DbOperationException;
import com.spring.hrmanagement.exception.ResourceNotFoundException;
import com.spring.hrmanagement.service.JobService;
import com.spring.hrmanagement.service.UserService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobServiceImpl implements JobService {

    JobRepository jobRepository;

    UserService userService;

    public JobServiceImpl (JobRepository jobRepository, UserService userService) {

        this.jobRepository = jobRepository;

        this.userService = userService;
    }

    /**
     * Create Job with given parameters
     *
     * @param dto       New Job Details
     * @param username  username of user that creates job
     * @return          Integer
     */
    @Override
    public Integer createJob(JobListingDto dto, String username) {

        String title = dto.getTitle();
        String description = dto.getDescription();
        Integer numberOfPeople = dto.getNumberOfPeople();
        Date lastApplicantDate = dto.getLastApplicationDate();

        Boolean jobExists = checkIfJobExists(title);

        if(jobExists)
            throw new ApiRequestException("Job with title = " + title + " already exists");

        Job job = new Job();
        job.setTitle(title);
        job.setDescription(description);
        job.setNumberOfPeople(numberOfPeople);
        job.setLastApplicationDate(new Timestamp(lastApplicantDate.getTime()));

        User user = userService.findByUsername(username);
        job.setUser(user);

        saveJob(job);

        return job.getId();
    }

    /**
     * Checks database if given job is already exists
     *
     * @param  title Job title
     * @return Boolean
     */
    private Boolean checkIfJobExists(String title) {
        Boolean jobExists;
        try {
            jobExists = jobRepository.existsByTitle(title);
        } catch(Exception e) {
            throw new DbOperationException("Error occured when checking existence of a job with name = "+ title);
        }
        return jobExists;
    }

    /**
     * Saves job instance to db
     *
     * @param job Job Object
     */
    private void saveJob(Job job) {
        try {
            jobRepository.save(job);
        } catch(Exception ex) {
            throw new DbOperationException("Unable to save user to db");
        }
    }

    @Override
    public List<JobListingDto> getAllJobs() {

        try {
            return jobRepository.findAll().stream().map(this::entityToDto).collect(Collectors.toList());
        } catch(Exception ex) {
            throw new DbOperationException("Unable to get Jobs");
        }
    }

    @Override
    public JobListingDto getJobListingById(Integer jobListingId) {
        Job job = getJobById(jobListingId);

        if(job == null)
            throw new ResourceNotFoundException("Job with id = " + jobListingId + " does not exists");

        return new JobListingDto(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getNumberOfPeople(),
                job.getLastApplicationDate()
        );
    }

    @Override
    public void deleteJobListing(Integer jobListingId) {

        Job job = getJobById(jobListingId);

        if(job == null)
            throw new ResourceNotFoundException("Job with id = " + jobListingId + " does not exists");

        try {
            jobRepository.delete(job);
        } catch(Exception ex) {
            throw new DbOperationException("Unable to delete job with id = " + jobListingId);
        }
    }

    @Override
    public Job getJobById(Integer jobListingId) {

        try {
            return jobRepository.findFirstById(jobListingId);
        } catch(Exception ex) {
            throw new ResourceNotFoundException("Job with id = " + jobListingId + " does not exists");
        }
    }

    private JobListingDto entityToDto(Job job) {

        JobListingDto dto = new JobListingDto();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setNumberOfPeople(job.getNumberOfPeople());
        dto.setLastApplicationDate(job.getLastApplicationDate());

        return dto;
    }
}
