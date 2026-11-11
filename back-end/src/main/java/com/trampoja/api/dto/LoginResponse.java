package com.trampoja.api.dto;

public class LoginResponse {

    private String token;
    private String tipoUsuario;
    private Long id;
    private String nome;
    private String email;

    public LoginResponse(String token, String tipoUsuario, Long id, String nome, String email) {
        this.token = token;
        this.tipoUsuario = tipoUsuario;
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}
