package com.trampoja.api.service;

import com.trampoja.api.model.Freelancer;
import com.trampoja.api.repository.FreelancerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreelancerService {

    private final FreelancerRepository freelancerRepository;

    public FreelancerService(FreelancerRepository freelancerRepository) {
        this.freelancerRepository = freelancerRepository;
    }

    public List<Freelancer> getAll() {
        return freelancerRepository.findAll();
    }

    public Freelancer getById(Long id) {
        return freelancerRepository.findById(id)
                .orElse(null);
    }

    public Freelancer create(Freelancer freelancer) {
        return freelancerRepository.save(freelancer);
    }

    public Freelancer update(Long id, Freelancer freelancerAtualizado) {

        Freelancer freelancer = freelancerRepository.findById(id)
                .orElse(null);

        if (freelancer == null) {
            return null;
        }

        freelancer.setNome(freelancerAtualizado.getNome());
        freelancer.setCpf(freelancerAtualizado.getCpf());
        freelancer.setTelefone(freelancerAtualizado.getTelefone());
        freelancer.setEmail(freelancerAtualizado.getEmail());
        freelancer.setResumo(freelancerAtualizado.getResumo());
        freelancer.setHabilidades(freelancerAtualizado.getHabilidades());
        freelancer.setEndereco(freelancerAtualizado.getEndereco());
        freelancer.setValorHora(freelancerAtualizado.getValorHora());

        return freelancerRepository.save(freelancer);
    }

    public boolean delete(Long id) {

        if (!freelancerRepository.existsById(id)) {
            return false;
        }

        freelancerRepository.deleteById(id);
        return true;
    }
}