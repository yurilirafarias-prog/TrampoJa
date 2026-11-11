package com.trampoja.api.service;

import com.trampoja.api.dto.LoginResponse;
import com.trampoja.api.exception.CredenciaisInvalidasException;
import com.trampoja.api.exception.EmailJaCadastradoException;
import com.trampoja.api.model.Freelancer;
import com.trampoja.api.repository.FreelancerRepository;
import com.trampoja.api.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreelancerService {

    private static final String TIPO_USUARIO = "FREELANCER";

    private final FreelancerRepository freelancerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public FreelancerService(FreelancerRepository freelancerRepository,  PasswordEncoder passwordEncoder,
                             JwtService jwtService) {
        this.freelancerRepository = freelancerRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Freelancer> getAll() {
        return freelancerRepository.findAll();
    }

    public Freelancer getById(Long id) {
        return freelancerRepository.findById(id)
                .orElse(null);
    }

    public LoginResponse login(@Valid Freelancer request) {
        Freelancer freelancer = freelancerRepository.findByEmail(request.getEmail())
                .orElseThrow(CredenciaisInvalidasException::new);

        if (!passwordEncoder.matches(request.getSenha(), freelancer.getSenha())) {
            throw new CredenciaisInvalidasException();
        }

        String token = jwtService.gerarToken(freelancer.getEmail(), freelancer.getId(), TIPO_USUARIO);

        return new LoginResponse(token, TIPO_USUARIO, freelancer.getId(), freelancer.getNome(), freelancer.getEmail());
    }

    public Freelancer create(Freelancer freelancer) {
        if (freelancerRepository.existsByEmail(freelancer.getEmail())) {
            throw new EmailJaCadastradoException(freelancer.getEmail());
        }

        freelancer.setSenha(passwordEncoder.encode(freelancer.getSenha()));

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
        freelancer.setSenha(freelancerAtualizado.getSenha());
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