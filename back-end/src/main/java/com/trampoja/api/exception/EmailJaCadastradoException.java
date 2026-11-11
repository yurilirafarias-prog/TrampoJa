package com.trampoja.api.exception;

public class EmailJaCadastradoException extends RuntimeException {

    public EmailJaCadastradoException(String email) {
        super("Já existe um cadastro com o e-mail " + email);
    }
}
