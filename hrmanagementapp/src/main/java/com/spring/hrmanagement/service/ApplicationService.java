package com.spring.hrmanagement.service;

import com.spring.hrmanagement.domain.dto.ApplicationDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ApplicationService {

    Integer createApplication(String dto, Integer jobId, MultipartFile file);

    List<ApplicationDto> listAllApplications(Integer jobId);

    ApplicationDto getOneApplicationById(Integer jobId, Integer applicationId);

}
