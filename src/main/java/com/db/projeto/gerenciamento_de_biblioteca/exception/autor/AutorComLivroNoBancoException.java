package com.db.projeto.gerenciamento_de_biblioteca.exception.autor;

public class AutorComLivroNoBancoException extends RuntimeException {
    public AutorComLivroNoBancoException(String mensagem){
        super(mensagem);
    }
}