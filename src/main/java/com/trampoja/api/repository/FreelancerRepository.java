package com.trampoja.api.repository;

import com.trampoja.api.model.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {
}