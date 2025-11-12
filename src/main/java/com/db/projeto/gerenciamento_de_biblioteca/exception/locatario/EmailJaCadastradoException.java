package com.db.projeto.gerenciamento_de_biblioteca.exception.locatario;

public class EmailJaCadastradoException extends RuntimeException {
    public EmailJaCadastradoException(String email){
        super("Já existe um locatário registrado para o email \'{"+email+"}\'");
    }
}