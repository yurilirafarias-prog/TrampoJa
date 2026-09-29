package com.trampoja.api.controller;

import com.trampoja.api.model.Freelancer;
import com.trampoja.api.service.FreelancerService;
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

    @PostMapping
    public Freelancer create(@RequestBody Freelancer novoFreelancer) {
        return freelancerService.create(novoFreelancer);
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