package com.trampoja.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trampoja.api.model.enums.TipoRemuneracao;
import com.trampoja.api.model.enums.Turno;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record VacancyRequest(

        @NotNull(message = "O id da empresa é obrigatório")
        Long empresaId,

        @NotBlank(message = "O título é obrigatório")
        String titulo,

        @NotBlank(message = "A categoria é obrigatória")
        String categoria,

        @NotNull(message = "O valor é obrigatório")
        @Positive(message = "O valor deve ser maior que zero")
        BigDecimal valor,

        @NotNull(message = "O tipo de remuneração é obrigatório (DIARIA ou HORA)")
        TipoRemuneracao tipoRemuneracao,

        @NotNull(message = "A data é obrigatória")
        LocalDate data,

        @NotNull(message = "O turno é obrigatório (MANHA, TARDE, NOITE ou MADRUGADA)")
        Turno turno,

        @NotNull(message = "A hora de início é obrigatória")
        @JsonFormat(pattern = "HH:mm")
        LocalTime horaInicio,

        @NotNull(message = "A hora de término é obrigatória")
        @JsonFormat(pattern = "HH:mm")
        LocalTime horaFim,

        String descricao,

        List<@NotBlank(message = "Requisito não pode ser vazio") String> requisitos,

        @NotBlank(message = "O endereço é obrigatório")
        String endereco,

        @NotBlank(message = "A cidade é obrigatória")
        String cidade,

        @NotBlank(message = "O estado é obrigatório")
        @Size(min = 2, max = 2, message = "Use a sigla do estado, ex.: SP")
        String estado
) {
}
