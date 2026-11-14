package com.trampoja.api.service;

import com.trampoja.api.dto.VacancyRequest;
import com.trampoja.api.dto.VacancyResponse;
import com.trampoja.api.exception.RecursoNaoEncontradoException;
import com.trampoja.api.model.Empresa;
import com.trampoja.api.model.Vacancy;
import com.trampoja.api.model.enums.VacancyStatus;
import com.trampoja.api.repository.EmpresaRepository;
import com.trampoja.api.repository.VacancyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class VacancyService {

    private final VacancyRepository vacancyRepository;
    private final EmpresaRepository empresaRepository;

    public VacancyService(VacancyRepository vacancyRepository, EmpresaRepository empresaRepository) {
        this.vacancyRepository = vacancyRepository;
        this.empresaRepository = empresaRepository;
    }

    @Transactional(readOnly = true)
    public List<VacancyResponse> listar(VacancyStatus status) {
        List<Vacancy> vagas = (status == null)
                ? vacancyRepository.findAllByOrderByDataAscHoraInicioAsc()
                : vacancyRepository.findByStatusOrderByDataAscHoraInicioAsc(status);

        return vagas.stream().map(VacancyResponse::from).toList();
    }


    @Transactional(readOnly = true)
    public List<VacancyResponse> listarPorEmpresa(Long empresaId, VacancyStatus status) {
        if (!empresaRepository.existsById(empresaId)) {
            throw new RecursoNaoEncontradoException("Empresa não encontrada");
        }

        List<Vacancy> vagas = (status == null)
                ? vacancyRepository.findByEmpresaIdOrderByDataAscHoraInicioAsc(empresaId)
                : vacancyRepository.findByEmpresaIdAndStatusOrderByDataAscHoraInicioAsc(empresaId, status);

        return vagas.stream().map(VacancyResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public VacancyResponse buscarPorId(Long id) {
        return VacancyResponse.from(buscarVaga(id));
    }

    @Transactional
    public VacancyResponse criar(VacancyRequest request) {
        Empresa empresa = empresaRepository.findById(request.empresaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa não encontrada"));

        Vacancy vaga = new Vacancy();
        vaga.setEmpresa(empresa);
        vaga.setStatus(VacancyStatus.ABERTA);
        preencherCampos(vaga, request);

        return VacancyResponse.from(vacancyRepository.save(vaga));
    }

    @Transactional
    public VacancyResponse atualizar(Long id, VacancyRequest request) {
        Vacancy vaga = buscarVaga(id);
        preencherCampos(vaga, request);

        return VacancyResponse.from(vacancyRepository.save(vaga));
    }

    @Transactional
    public VacancyResponse encerrar(Long id) {
        Vacancy vaga = buscarVaga(id);
        vaga.setStatus(VacancyStatus.ENCERRADA);

        return VacancyResponse.from(vacancyRepository.save(vaga));
    }

    @Transactional
    public void excluir(Long id) {
        vacancyRepository.delete(buscarVaga(id));
    }

    private Vacancy buscarVaga(Long id) {
        return vacancyRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Vaga não encontrada"));
    }

    private void preencherCampos(Vacancy vaga, VacancyRequest request) {
        vaga.setTitulo(request.titulo().trim());
        vaga.setCategoria(request.categoria().trim());
        vaga.setValor(request.valor());
        vaga.setTipoRemuneracao(request.tipoRemuneracao());
        vaga.setData(request.data());
        vaga.setTurno(request.turno());
        vaga.setHoraInicio(request.horaInicio());
        vaga.setHoraFim(request.horaFim());
        vaga.setDescricao(request.descricao());
        vaga.setEndereco(request.endereco().trim());
        vaga.setCidade(request.cidade().trim());
        vaga.setEstado(request.estado().trim().toUpperCase());

        vaga.getRequisitos().clear();
        if (request.requisitos() != null) {
            vaga.getRequisitos().addAll(new ArrayList<>(request.requisitos()));
        }
    }
}
