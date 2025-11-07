package com.db.projeto.gerenciamento_de_biblioteca.service;

import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.NovoLivroDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.CategoriaDoLivro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LivroServiceI {
    LivroResponseDto cadastrar(NovoLivroDto dto);
    LivroResponseDto atualizar(Long id, LivroAtualizacoesDto dto);
    void apagar(Long id);
    Page<LivroResponseDto> listarTodos(int status, Pageable pageable);
    Page<LivroResponseDto> listarTodos(Pageable pageable);
    LivroResponseDto buscarPorId(Long id);
    Page<LivroResponseDto> buscarPorTitulo(String titulo, Pageable pageable);
    Page<LivroResponseDto> buscarPorCategoria(CategoriaDoLivro categoriaDoLivro, Pageable pageable);
    Page<LivroResponseDto> buscarPorAutor(Long idAutor, Pageable pageable);}