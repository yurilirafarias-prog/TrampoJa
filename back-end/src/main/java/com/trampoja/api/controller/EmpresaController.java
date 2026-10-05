package com.trampoja.api.controller;

import com.trampoja.api.model.Empresa;
import com.trampoja.api.service.EmpresaService;
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

    @GetMapping("/{id")
    public ResponseEntity<Empresa> getById(@PathVariable Long id){

        Empresa empresa = empresaService.getById(id);

        if (empresa == null) {
            return ResponseEntity.notfound().build();
        }

        return ResponseEntity.ok(empresa);
    }

    @PostMapping
    public Empresa create(@RequestBody Empresa empresa) {
        return empresaService.create(empresa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> update(
            @PathVariable Long id,
            @RequestBody Empresa empresaAtualizado){

        Empresa empresa = empresaService.update(id, empresaAtualizado);

        if (empresa == null ){
            return ResponseEntity.notfound().build();
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