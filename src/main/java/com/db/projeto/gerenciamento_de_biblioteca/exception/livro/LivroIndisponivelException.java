package com.db.projeto.gerenciamento_de_biblioteca.exception.livro;

public class LivroIndisponivelException extends RuntimeException {
    public LivroIndisponivelException(Long id){
        super("O livro para o ID: #{"+id+"}, está indisponível.");
    }
}