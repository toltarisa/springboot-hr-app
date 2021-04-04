package com.spring.hrmanagement.domain.repository;

import com.spring.hrmanagement.domain.model.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {


    List<Application> findAllByJobId(Integer jobId);

    Application findByIdAndJobId(Integer applicationId, Integer jobId);

    Application findFirstById(Integer id);
}
