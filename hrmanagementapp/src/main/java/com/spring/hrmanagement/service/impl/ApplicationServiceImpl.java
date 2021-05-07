package com.spring.hrmanagement.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.hrmanagement.domain.dto.ApplicationDto;
import com.spring.hrmanagement.domain.dto.JobListingDto;
import com.spring.hrmanagement.domain.model.Application;
import com.spring.hrmanagement.domain.model.Job;
import com.spring.hrmanagement.domain.repository.ApplicationRepository;
import com.spring.hrmanagement.exception.DbOperationException;
import com.spring.hrmanagement.service.ApplicationService;
import com.spring.hrmanagement.service.AwsS3Service;
import com.spring.hrmanagement.service.JobService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationServiceImpl implements ApplicationService {

    static final Logger log = LoggerFactory.getLogger(ApplicationServiceImpl.class);

    ApplicationRepository applicationRepository;
    AwsS3Service awsS3Service;
    JobService jobService;

    public ApplicationServiceImpl (ApplicationRepository applicationRepository, AwsS3Service awsS3Service,
                                   JobService jobService) {

        this.applicationRepository = applicationRepository;
        this.awsS3Service = awsS3Service;
        this.jobService = jobService;
    }

    @Override
    public Integer createApplication(String applicationDto, Integer jobId, MultipartFile file) {

        Application application = new Application();
        ApplicationDto dto = stringToJson(applicationDto);

        application.setName(dto.getName());
        application.setEmail(dto.getEmail());
        application.setAddress(dto.getAddress());
        application.setPhone(dto.getPhone());
        application.setThoughts(dto.getThoughts());

        String fileUrl = awsS3Service.uploadFile(file);
        application.setFileUrl(fileUrl);

        Job job = jobService.getJobById(jobId);
        application.setJob(job);

        try {
            applicationRepository.save(application);
        } catch(Exception ex) {
            throw new DbOperationException("Unable to save application to db");
        }

        return application.getId();
    }

    @Override
    public List<ApplicationDto> listAllApplications(Integer jobId) {

        try {
            return applicationRepository.findAllByJobId(jobId)
                    .stream()
                    .map(this::entityToDto)
                    .collect(Collectors.toList());
        } catch(Exception ex) {
            throw new DbOperationException("Unable to list applications");
        }
    }

    @Override
    public ApplicationDto getOneApplicationById(Integer applicationId, Integer jobId) {

        Application application;
        try {
            application = applicationRepository.findByIdAndJobId(applicationId,jobId);
        } catch(Exception ex) {
            throw new DbOperationException("Unable to get application with id = "+applicationId);
        }

        return entityToDto(application);
    }

    private ApplicationDto entityToDto(Application application) {

        ApplicationDto dto = new ApplicationDto();

        dto.setId(application.getId());
        dto.setName(application.getName());
        dto.setEmail(application.getEmail());
        dto.setAddress(application.getAddress());
        dto.setPhone(application.getPhone());
        dto.setThoughts(application.getThoughts());
        dto.setFileUrl(application.getFileUrl());

        return dto;
    }

    private ApplicationDto stringToJson(String json) {
        ApplicationDto dto = new ApplicationDto();

        try {
            ObjectMapper mapper = new ObjectMapper();
            dto = mapper.readValue(json, ApplicationDto.class);
        } catch(IOException ex) {
            log.error(ex.getMessage(), ex);
        }

        return dto;
    }
}
