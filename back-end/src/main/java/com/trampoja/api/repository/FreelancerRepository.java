package com.trampoja.api.repository;

import com.trampoja.api.model.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {
    Optional<Freelancer> findByEmail(String email);

    boolean existsByEmail(String email);

}