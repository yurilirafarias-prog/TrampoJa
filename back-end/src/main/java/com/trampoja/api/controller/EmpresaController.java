package com.trampoja.api.controller;

import com.trampoja.api.model.Empresa;
import com.trampoja.api.service.EmpresaService;
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

    @PostMapping
    public Empresa create(@RequestBody Empresa empresa) {
        return empresaService.create(empresa);
    }
}