package com.trampoja.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trampoja.api.model.Vacancy;
import com.trampoja.api.model.enums.TipoRemuneracao;
import com.trampoja.api.model.enums.Turno;
import com.trampoja.api.model.enums.VacancyStatus;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record VacancyResponse(
        Long id,
        String titulo,
        String categoria,
        VacancyStatus status,
        BigDecimal valor,
        TipoRemuneracao tipoRemuneracao,
        LocalDate data,
        Turno turno,
        @JsonFormat(pattern = "HH:mm") LocalTime horaInicio,
        @JsonFormat(pattern = "HH:mm") LocalTime horaFim,
        double duracaoHoras,
        String descricao,
        List<String> requisitos,
        String endereco,
        String cidade,
        String estado,
        Long empresaId,
        String empresaNome,
        LocalDateTime dataCriacao
) {

    public static VacancyResponse from(Vacancy vaga) {
        return new VacancyResponse(
                vaga.getId(),
                vaga.getTitulo(),
                vaga.getCategoria(),
                vaga.getStatus(),
                vaga.getValor(),
                vaga.getTipoRemuneracao(),
                vaga.getData(),
                vaga.getTurno(),
                vaga.getHoraInicio(),
                vaga.getHoraFim(),
                calcularDuracaoHoras(vaga.getHoraInicio(), vaga.getHoraFim()),
                vaga.getDescricao(),
                List.copyOf(vaga.getRequisitos()),
                vaga.getEndereco(),
                vaga.getCidade(),
                vaga.getEstado(),
                vaga.getEmpresa().getId(),
                vaga.getEmpresa().getNome(),
                vaga.getDataCriacao()
        );
    }

    private static double calcularDuracaoHoras(LocalTime inicio, LocalTime fim) {
        long minutos = Duration.between(inicio, fim).toMinutes();
        if (minutos <= 0) {
            minutos += 24 * 60;
        }
        return minutos / 60.0;
    }
}
