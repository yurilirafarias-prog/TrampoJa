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

    public Empresa getById(Long id){
        return empresaRepository.findById(id)
                .orElse(null);
    }

    public Empresa create(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    public Empresa update(Long id, Empresa empresaAtualizado){

        Empresa empresa = empresaRepository.findById(id)
                .orElse(null);

        if (empresa == null){
            return null;
        }

        empresa.setNome(empresaAtualizado.getNome());
        empresa.setCnpj(empresaAtualizado.getCnpj());
        empresa.setRazaoSocial(empresaAtualizado.getRazaoSocial());
        empresa.setEndereco(empresaAtualizado.getEndereco());

        return empresaRepository.save(empresa);
    }

    public boolean delete(Long id){

        if (!empresaRepository.existsById(id)){
            return false;
        }

        empresaRepository.deleteById(id);
        return true;
    }
}