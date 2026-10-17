package com.trampoja.api.controller;

import com.trampoja.api.dto.OportunidadeRequest;
import com.trampoja.api.model.Oportunidade;
import com.trampoja.api.service.OportunidadeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/oportunidades")
public class OportunidadeController {

    private final OportunidadeService oportunidadeService;

    public OportunidadeController(OportunidadeService oportunidadeService) {
        this.oportunidadeService = oportunidadeService;
    }

    // Listar todas as oportunidades
    @GetMapping
    public ResponseEntity<List<Oportunidade>> listarTodas() {
        return ResponseEntity.ok(oportunidadeService.listarTodas());
    }

    // Buscar oportunidade por ID
    @GetMapping("/{id}")
    public ResponseEntity<Oportunidade> buscarPorId(@PathVariable Long id) {
        return oportunidadeService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Cadastrar nova oportunidade
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody OportunidadeRequest request) {
        try {
            Oportunidade oportunidade = oportunidadeService.salvar(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(oportunidade);

        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // Atualizar oportunidade existente
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @RequestBody OportunidadeRequest request) {

        if (oportunidadeService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        try {
            Oportunidade oportunidadeAtualizada =
                    oportunidadeService.atualizar(id, request);

            return ResponseEntity.ok(oportunidadeAtualizada);

        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    // Excluir oportunidade
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (oportunidadeService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        oportunidadeService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
