package com.trampoja.api.controller;

import com.trampoja.api.dto.VacancyRequest;
import com.trampoja.api.dto.VacancyResponse;
import com.trampoja.api.exception.RecursoNaoEncontradoException;
import com.trampoja.api.model.enums.VacancyStatus;
import com.trampoja.api.service.VacancyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vagas")
public class VacancyController {

    private final VacancyService vacancyService;

    public VacancyController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }
    
    @GetMapping
    public ResponseEntity<List<VacancyResponse>> listar(@RequestParam(required = false) VacancyStatus status) {
        return ResponseEntity.ok(vacancyService.listar(status));
    }

    @GetMapping("/empresa/{empresaId}")
    public ResponseEntity<?> listarPorEmpresa(
            @PathVariable Long empresaId,
            @RequestParam(required = false) VacancyStatus status) {
        try {
            List<VacancyResponse> vagas = vacancyService.listarPorEmpresa(empresaId, status);
            return ResponseEntity.ok(vagas);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            VacancyResponse vaga = vacancyService.buscarPorId(id);
            return ResponseEntity.ok(vaga);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid VacancyRequest request) {
        try {
            VacancyResponse vaga = vacancyService.criar(request);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(vaga);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody @Valid VacancyRequest request) {
        try {
            VacancyResponse vaga = vacancyService.atualizar(id, request);
            return ResponseEntity.ok(vaga);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/encerrar")
    public ResponseEntity<?> encerrar(@PathVariable Long id) {
        try {
            VacancyResponse vaga = vacancyService.encerrar(id);
            return ResponseEntity.ok(vaga);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        try {
            vacancyService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
}
