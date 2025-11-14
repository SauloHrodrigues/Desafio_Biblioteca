package com.db.projeto.gerenciamento_de_biblioteca.exception.aluguel;

public class AluguelNaoEncontradoException extends RuntimeException {
    public AluguelNaoEncontradoException(Long id){
        super("Não foi localizado nenhum aluguel com o ID: #{"+id+"}");
    }
    public AluguelNaoEncontradoException(String mensagem){
        super(mensagem);
    }
}