package com.trampoja.api.controller;

import com.trampoja.api.dto.LoginRequest;
import com.trampoja.api.dto.LoginResponse;
import com.trampoja.api.exception.CredenciaisInvalidasException;
import com.trampoja.api.exception.EmailJaCadastradoException;
import com.trampoja.api.model.Empresa;
import com.trampoja.api.model.Freelancer;
import com.trampoja.api.service.FreelancerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/freelancer")
public class FreelancerController {

    private final FreelancerService freelancerService;

    public FreelancerController(FreelancerService freelancerService) {
        this.freelancerService = freelancerService;
    }

    @GetMapping
    public List<Freelancer> getAll() {
        return freelancerService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Freelancer> getById(@PathVariable Long id) {

        Freelancer freelancer = freelancerService.getById(id);

        if (freelancer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(freelancer);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid Freelancer freelancer) {
        try {
            LoginResponse response = freelancerService.login(freelancer);
            return ResponseEntity.ok(response);
        } catch (CredenciaisInvalidasException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> create(@RequestBody Freelancer freelancer) {
        if (freelancer.getEmail() == null || freelancer.getEmail().isBlank()
                || freelancer.getSenha() == null || freelancer.getSenha().isBlank()) {
            return ResponseEntity.badRequest().body("E-mail e senha são obrigatórios");
        }

        try {
            Freelancer criada = freelancerService.create(freelancer);
            return ResponseEntity.status(HttpStatus.CREATED).body(criada);
        } catch (EmailJaCadastradoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Freelancer> update(
            @PathVariable Long id,
            @RequestBody Freelancer freelancerAtualizado) {

        Freelancer freelancer = freelancerService.update(id, freelancerAtualizado);

        if (freelancer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(freelancer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        boolean deletado = freelancerService.delete(id);

        if (!deletado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}