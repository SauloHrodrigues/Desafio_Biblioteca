package com.db.projeto.gerenciamento_de_biblioteca.exception.autor;

public class AutorSemLivroNoBancoException extends RuntimeException {
    public AutorSemLivroNoBancoException(String mensagem){
        super(mensagem);
    }
}