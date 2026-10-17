package com.trampoja.api.service;

import com.trampoja.api.dto.OportunidadeRequest;
import com.trampoja.api.model.Empresa;
import com.trampoja.api.model.Oportunidade;
import com.trampoja.api.repository.EmpresaRepository;
import com.trampoja.api.repository.OportunidadeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OportunidadeService {

    private final OportunidadeRepository oportunidadeRepository;
    private final EmpresaRepository empresaRepository;

    public OportunidadeService(
            OportunidadeRepository oportunidadeRepository,
            EmpresaRepository empresaRepository) {

        this.oportunidadeRepository = oportunidadeRepository;
        this.empresaRepository = empresaRepository;
    }

    public List<Oportunidade> listarTodas() {
        return oportunidadeRepository.findAll();
    }

    public Optional<Oportunidade> buscarPorId(Long id) {
        return oportunidadeRepository.findById(id);
    }

    public Oportunidade salvar(OportunidadeRequest request) {

        Empresa empresa = empresaRepository.findById(request.getEmpresaId())
                .orElseThrow(() ->
                        new NoSuchElementException("Empresa não encontrada"));

        Oportunidade oportunidade = new Oportunidade();

        oportunidade.setTitulo(request.getTitulo());
        oportunidade.setDescricao(request.getDescricao());
        oportunidade.setModalidade(request.getModalidade());
        oportunidade.setRemuneracao(request.getRemuneracao());
        oportunidade.setDataPublicacao(request.getDataPublicacao());
        oportunidade.setDataEncerramento(request.getDataEncerramento());
        oportunidade.setAtiva(request.isAtiva());
        oportunidade.setEmpresa(empresa);

        return oportunidadeRepository.save(oportunidade);
    }

    public Oportunidade atualizar(Long id, OportunidadeRequest request) {

        Oportunidade oportunidade = oportunidadeRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("Oportunidade não encontrada"));

        Empresa empresa = empresaRepository.findById(request.getEmpresaId())
                .orElseThrow(() ->
                        new NoSuchElementException("Empresa não encontrada"));

        oportunidade.setTitulo(request.getTitulo());
        oportunidade.setDescricao(request.getDescricao());
        oportunidade.setModalidade(request.getModalidade());
        oportunidade.setRemuneracao(request.getRemuneracao());
        oportunidade.setDataPublicacao(request.getDataPublicacao());
        oportunidade.setDataEncerramento(request.getDataEncerramento());
        oportunidade.setAtiva(request.isAtiva());
        oportunidade.setEmpresa(empresa);

        return oportunidadeRepository.save(oportunidade);
    }


    public void excluir(Long id) {
        oportunidadeRepository.deleteById(id);
    }
}
