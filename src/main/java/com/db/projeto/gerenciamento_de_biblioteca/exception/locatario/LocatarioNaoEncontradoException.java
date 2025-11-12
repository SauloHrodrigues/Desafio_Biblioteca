package com.db.projeto.gerenciamento_de_biblioteca.exception.locatario;

public class LocatarioNaoEncontradoException extends RuntimeException {
    public LocatarioNaoEncontradoException(Long id){
        super("Não foi possível localizar nenhum locatário para o ID: #{%d}".formatted(id));
    }
}