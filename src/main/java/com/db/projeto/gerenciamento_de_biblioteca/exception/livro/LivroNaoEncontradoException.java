package com.db.projeto.gerenciamento_de_biblioteca.exception.livro;

import com.db.projeto.gerenciamento_de_biblioteca.enuns.CategoriaDoLivro;

public class LivroNaoEncontradoException extends RuntimeException {
    public LivroNaoEncontradoException(String objetoProcurado, String objeto){
        super("Não foi localizado nenhum livro para o "+objetoProcurado.toLowerCase()+" \'{"+objeto+"}\'");
    }
    public LivroNaoEncontradoException(Long id){
        super("Não foi localizado nenhum livro para o ID: #{"+id+"}");
    }
}