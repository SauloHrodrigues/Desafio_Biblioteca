package com.db.projeto.gerenciamento_de_biblioteca.exception.autor;

public class AutorNaoInformadoException extends RuntimeException {
    public AutorNaoInformadoException(String mensagem){
        super(mensagem);
    }
}