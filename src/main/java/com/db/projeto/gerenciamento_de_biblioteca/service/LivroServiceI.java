package com.db.projeto.gerenciamento_de_biblioteca.service;

import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.NovoLivroDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.CategoriaDoLivro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LivroServiceI {
    LivroResponseDto cadastrar(NovoLivroDto dto);
    LivroResponseDto atualizar(LivroAtualizacoesDto dto);
    void apagar(Long id);
    Page<LivroResponseDto> listarTodos(Pageable pageable);
    LivroResponseDto buscarPorId(Long id);
    LivroResponseDto buscarPorTitulo(String titulo);
    Page<LivroResponseDto> buscarPorCategoria(CategoriaDoLivro categoriaDoLivro);
    Page<LivroResponseDto> buscarPorAutor(CategoriaDoLivro categoriaDoLivro);


}