package com.trampoja.api.service;

import com.trampoja.api.dto.LoginRequest;
import com.trampoja.api.dto.LoginResponse;
import com.trampoja.api.exception.CredenciaisInvalidasException;
import com.trampoja.api.exception.EmailJaCadastradoException;
import com.trampoja.api.model.Empresa;
import com.trampoja.api.repository.EmpresaRepository;
import com.trampoja.api.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaService {

    private static final String TIPO_USUARIO = "EMPRESA";

    private final EmpresaRepository empresaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public EmpresaService(EmpresaRepository empresaRepository,
                          PasswordEncoder passwordEncoder,
                          JwtService jwtService) {
        this.empresaRepository = empresaRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public List<Empresa> getAll() {
        return empresaRepository.findAll();
    }

    public Empresa getById(Long id){
        return empresaRepository.findById(id)
                .orElse(null);
    }

    public Empresa create(Empresa empresa) {
        if (empresaRepository.existsByEmail(empresa.getEmail())) {
            throw new EmailJaCadastradoException(empresa.getEmail());
        }

        // Nunca salvar a senha em texto puro
        empresa.setSenha(passwordEncoder.encode(empresa.getSenha()));
        return empresaRepository.save(empresa);
    }

    public LoginResponse login(LoginRequest request) {
        Empresa empresa = empresaRepository.findByEmail(request.getEmail())
                .orElseThrow(CredenciaisInvalidasException::new);

        if (!passwordEncoder.matches(request.getSenha(), empresa.getSenha())) {
            throw new CredenciaisInvalidasException();
        }

        String token = jwtService.gerarToken(empresa.getEmail(), empresa.getId(), TIPO_USUARIO);

        return new LoginResponse(token, TIPO_USUARIO, empresa.getId(), empresa.getNome(), empresa.getEmail());
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
