package com.db.projeto.gerenciamento_de_biblioteca.fixture;

import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.NovoLivroDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.CategoriaDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.model.Autor;
import com.db.projeto.gerenciamento_de_biblioteca.model.Livro;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LivroFixture {
    static Long id =0L;

    public static NovoLivroDto request(String titulo, LocalDate publicacao, String isbn,
                                       CategoriaDoLivro categoriaDoLivro, List<Long> idAurores){
        return new NovoLivroDto(
             titulo,publicacao,isbn,categoriaDoLivro,idAurores
        );
    }

    public static Livro entity(NovoLivroDto dto, Autor autor){

        return entity(
                dto.titulo(), dto.publicacao(), dto.isbn(), dto.categoriaDoLivro(), autor
        );
    }

    public static Livro entity(String titulo, LocalDate publicacao, String isbn,
                               CategoriaDoLivro categoriaDoLivro, Autor autor){
        Set<Autor> autores = new HashSet<>();
        autores.add(autor);
       id++;
        return Livro.builder()
                .id(id)
                .titulo(titulo)
                .publicacao(publicacao)
                .isbn(isbn)
                .categoriaDoLivro(categoriaDoLivro)
                .autores(autores)
                .build();
    }

}
