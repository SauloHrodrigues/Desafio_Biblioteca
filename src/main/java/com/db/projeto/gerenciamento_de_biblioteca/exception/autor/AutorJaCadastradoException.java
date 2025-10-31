package com.db.projeto.gerenciamento_de_biblioteca.exception.autor;

public class AutorJaCadastradoException extends RuntimeException {
    public AutorJaCadastradoException(String mensagem){
        super(mensagem);
    }
}