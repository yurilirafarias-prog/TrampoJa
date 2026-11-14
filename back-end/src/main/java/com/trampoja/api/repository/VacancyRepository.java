package com.trampoja.api.repository;

import com.trampoja.api.model.Vacancy;
import com.trampoja.api.model.enums.VacancyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    List<Vacancy> findByEmpresaIdOrderByDataAscHoraInicioAsc(Long empresaId);

    List<Vacancy> findByEmpresaIdAndStatusOrderByDataAscHoraInicioAsc(Long empresaId, VacancyStatus status);

    List<Vacancy> findByStatusOrderByDataAscHoraInicioAsc(VacancyStatus status);

    List<Vacancy> findAllByOrderByDataAscHoraInicioAsc();
}
