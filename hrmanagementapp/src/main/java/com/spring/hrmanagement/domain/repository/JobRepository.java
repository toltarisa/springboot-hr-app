package com.spring.hrmanagement.domain.repository;

import com.spring.hrmanagement.domain.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Integer> {

    Boolean existsByTitle(String title);

    Job findFirstById(Integer id);
}
