package com.db.projeto.gerenciamento_de_biblioteca.exception.autor;

public class CpfJaCadastradoException extends RuntimeException {
    public CpfJaCadastradoException(String cpf) {
        super(String.format("Já existe um autor registrado para o CPF '%s'", cpf));
    }
}