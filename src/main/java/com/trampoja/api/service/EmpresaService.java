package com.trampoja.api.service;

import com.trampoja.api.model.Empresa;
import com.trampoja.api.repository.EmpresaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public List<Empresa> getAll() {
        return empresaRepository.findAll();
    }

    public Empresa create(Empresa empresa) {
        return empresaRepository.save(empresa);
    }
}