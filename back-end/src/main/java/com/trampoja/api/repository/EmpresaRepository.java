package com.trampoja.api.repository;

import com.trampoja.api.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Optional<Empresa> findByEmail(String email);

    boolean existsByEmail(String email);
}
