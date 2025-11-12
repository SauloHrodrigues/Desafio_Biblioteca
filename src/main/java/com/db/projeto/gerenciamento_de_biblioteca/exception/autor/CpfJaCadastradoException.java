package com.db.projeto.gerenciamento_de_biblioteca.exception.autor;

public class CpfJaCadastradoException extends RuntimeException {
    public CpfJaCadastradoException(String pessoa, String cpf) {
        super(String.format("Já existe um '%s' registrado para o CPF '%s'",pessoa, cpf));
    }
}