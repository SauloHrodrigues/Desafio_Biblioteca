package com.db.projeto.gerenciamento_de_biblioteca.exception.autor;

public class AutorNaoCadastradoException extends RuntimeException {
    public AutorNaoCadastradoException(String nome){
        super("O nome: "+nome.toUpperCase()+" não corresponde a nenhum autor cadastrado no nosso banco de dados.");
    }
    public AutorNaoCadastradoException(Long id){
        super("O id: "+id+" não corresponde a nenhum autor cadastrado no nosso banco de dados.");
    }
}