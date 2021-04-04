package com.spring.hrmanagement.domain.repository;

import com.spring.hrmanagement.domain.model.ERole;
import com.spring.hrmanagement.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}
