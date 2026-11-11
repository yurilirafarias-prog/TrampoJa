package com.trampoja.api.controller;
import com.trampoja.api.dto.LoginRequest;
import com.trampoja.api.dto.LoginResponse;
import com.trampoja.api.exception.CredenciaisInvalidasException;
import com.trampoja.api.exception.EmailJaCadastradoException;
import com.trampoja.api.model.Empresa;
import com.trampoja.api.service.EmpresaService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    public List<Empresa> getAll() {
        return empresaService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> getById(@PathVariable Long id){

        Empresa empresa = empresaService.getById(id);

        if (empresa == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(empresa);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginEmpresa(@RequestBody @Valid LoginRequest request) {
        try {
            LoginResponse response = empresaService.login(request);
            return ResponseEntity.ok(response);
        } catch (CredenciaisInvalidasException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }

    // POST /empresa/register
    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody Empresa empresa) {
        if (empresa.getEmail() == null || empresa.getEmail().isBlank()
                || empresa.getSenha() == null || empresa.getSenha().isBlank()) {
            return ResponseEntity.badRequest().body("E-mail e senha são obrigatórios");
        }

        try {
            Empresa criada = empresaService.create(empresa);
            return ResponseEntity.status(HttpStatus.CREATED).body(criada);
        } catch (EmailJaCadastradoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> update(
            @PathVariable Long id,
            @RequestBody Empresa empresaAtualizado){

        Empresa empresa = empresaService.update(id, empresaAtualizado);

        if (empresa == null ){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(empresa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        boolean deletado = empresaService.delete(id);

        if (!deletado){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}