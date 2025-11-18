package com.db.projeto.gerenciamento_de_biblioteca.exception.aluguel;

public class AluguelDevolvidoException extends RuntimeException {
    public AluguelDevolvidoException(Long id){
        super("O aluguel com o ID: #{"+id+"}, já foi devolvido;");
    }
}