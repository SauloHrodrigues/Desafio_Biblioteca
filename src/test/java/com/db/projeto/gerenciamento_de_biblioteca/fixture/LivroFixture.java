package com.db.projeto.gerenciamento_de_biblioteca.fixture;

import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AutorResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.NovoLivroDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.CategoriaDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.Sexo;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.StatusDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.model.Autor;
import com.db.projeto.gerenciamento_de_biblioteca.model.Livro;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LivroFixture {

    final static Livro getLivro01() {
        return Livro.builder()
                .id(1L)
                .titulo("Casais Inteligentes Enriquecem Juntos")
                .publicacao(LocalDate.of(2004, 1, 1))
                .isbn("978-85-4310-143-9")
                .status(StatusDoLivro.DISPONIVEL)
                .categoriaDoLivro(CategoriaDoLivro.ECONOMIA)
                .autores(Set.of(AutorFixture.AUTOR01()))
                .build();
    }

    final static Livro getLivro02() {
        return Livro.builder()
                .id(2L)
                .titulo("Romanceiro da Inconfidência")
                .publicacao(LocalDate.of(1953, 1, 1))
                .isbn("978-85-7164-219-3")
                .status(StatusDoLivro.DISPONIVEL)
                .categoriaDoLivro(CategoriaDoLivro.POESIA)
                .autores(Set.of(AutorFixture.AUTOR02()))
                .build();
    }



    public static NovoLivroDto requestLivo01() {
            return new NovoLivroDto(
                    getLivro01().getTitulo(), getLivro01().getPublicacao(), getLivro01().getIsbn(), getLivro01().getCategoriaDoLivro(),
                    List.of(getLivro01().getAutores().iterator().next().getId()));

    }

    public static NovoLivroDto requestLivo02() {
            return new NovoLivroDto(
                    getLivro02().getTitulo(), getLivro02().getPublicacao(), getLivro02().getIsbn(), getLivro02().getCategoriaDoLivro(),
                    List.of(getLivro02().getAutores().iterator().next().getId()));
    }

    public static Livro livro01(){
        return getLivro01();
    }

    public static Livro livro02(){
        return getLivro02();
    }

    public static Livro update(Livro livro, LivroAtualizacoesDto atualizacoesDto){
        String titulo= atualizacoesDto.titulo()== null ? livro.getTitulo() : atualizacoesDto.titulo();
        LocalDate pubricacao = atualizacoesDto.publicacao()== null ? livro.getPublicacao() : atualizacoesDto.publicacao();
        String isbn = atualizacoesDto.isbn() == null ? livro.getIsbn() : atualizacoesDto.isbn();
        CategoriaDoLivro categoriaDoLivro= atualizacoesDto.categoriaDoLivro() == null ? livro.getCategoriaDoLivro() : atualizacoesDto.categoriaDoLivro();

        livro.setTitulo(titulo);
        livro.setPublicacao(pubricacao);
        livro.setIsbn(isbn);
        livro.setCategoriaDoLivro(categoriaDoLivro);

        return livro;
    }
}