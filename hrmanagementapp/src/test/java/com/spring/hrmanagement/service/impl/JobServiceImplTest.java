package com.spring.hrmanagement.service.impl;

import com.spring.hrmanagement.domain.dto.JobListingDto;
import com.spring.hrmanagement.domain.model.Job;
import com.spring.hrmanagement.domain.repository.JobRepository;
import com.spring.hrmanagement.exception.ApiRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class JobServiceImplTest {

    @Mock
    JobRepository jobRepository;

    @InjectMocks
    JobServiceImpl jobService;

    @Test
    void Should_ReturnJob_When_CreateJob() {

        //given
        JobListingDto dto = new JobListingDto();
        dto.setTitle("Backend Developer");
        dto.setDescription("This is a 4 year experience required backend development job");
        dto.setNumberOfPeople(3);
        dto.setLastApplicationDate(new Date());

        //when
        when(jobRepository.save(any(Job.class))).thenReturn(new Job());

        Job savedJob = jobService.createJob(dto, "hrmanager");

        // then
        assertThat(savedJob.getTitle()).isEqualTo(dto.getTitle());
    }

    @Test
    void Should_ThrowApiRequestException_When_JobExists() {

        //given
        JobListingDto dto = new JobListingDto();
        dto.setTitle("Backend Developer");
        dto.setDescription("This is a 4 year experience required backend development job");
        dto.setNumberOfPeople(3);
        dto.setLastApplicationDate(new Date());


        //when
        when(jobRepository.existsByTitle(dto.getTitle())).thenReturn(true);

        // then
        assertThatThrownBy(() -> jobService.createJob(dto, "hrmanager"))
                .isInstanceOf(ApiRequestException.class)
                .hasMessageContaining("Job with title = " + dto.getTitle() + " already exists");
    }

    @Test
    void getAllJobs() {

        //when
        jobService.getAllJobs();

        //then
        verify(jobRepository).findAll();
    }

    @Test
    void getJobListingById() {
    }

    @Test
    void deleteJobListing() {
    }

    @Test
    void getJobById() {

        //given
        JobListingDto dto = new JobListingDto();
        dto.setId(1);
        dto.setTitle("Backend Developer");
        dto.setDescription("This is a 4 year experience required backend development job");
        dto.setNumberOfPeople(3);
        dto.setLastApplicationDate(new Date());

        Job job = new Job();
        job.setId(1);
        job.setTitle("Backend Developer");
        job.setDescription("This is a 4 year experience required backend development job");
        job.setNumberOfPeople(3);
        job.setLastApplicationDate(new Timestamp(System.currentTimeMillis()));

        when(jobRepository.findFirstById(1)).thenReturn(job);

        assertThat(jobService.getJobById(1)).isEqualTo(dto);
    }
}