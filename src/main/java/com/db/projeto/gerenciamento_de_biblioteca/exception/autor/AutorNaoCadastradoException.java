package com.db.projeto.gerenciamento_de_biblioteca.exception.autor;

public class AutorNaoCadastradoException extends RuntimeException {
    public AutorNaoCadastradoException(String nome){
        super("Não foi encontrado nenhum autor com o nome \'{"+nome+"}\'");
    }
    public AutorNaoCadastradoException(Long id){
        super("Não foi localizado nenhum autor para o ID: #{"+id+"}");
    }
}