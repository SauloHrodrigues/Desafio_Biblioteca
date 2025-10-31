package com.db.projeto.gerenciamento_de_biblioteca.exception.autor;

import com.db.projeto.gerenciamento_de_biblioteca.model.Autor;

public class AutorComLivroNoBancoException extends RuntimeException {
    public AutorComLivroNoBancoException(Autor autor){
        super("O autor: "+autor.getNome().toUpperCase()+
                " possue vínculo com "+autor.getLivros().size()+
                " livros. Assim, não pode ser excluído.");
    }
}