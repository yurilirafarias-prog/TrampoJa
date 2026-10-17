package com.trampoja.api.repository;

import com.trampoja.api.model.Oportunidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OportunidadeRepository
        extends JpaRepository<Oportunidade, Long> {
}
