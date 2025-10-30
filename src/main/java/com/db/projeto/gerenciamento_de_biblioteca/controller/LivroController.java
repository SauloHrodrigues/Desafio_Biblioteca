package com.db.projeto.gerenciamento_de_biblioteca.controller;

import com.db.projeto.gerenciamento_de_biblioteca.controller.intefaces_swagger.LivroSwaggerI;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.NovoLivroDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/livros")
public class LivroController implements LivroSwaggerI {

    @Override
    public ResponseEntity<LivroResponseDto> cadastrar(NovoLivroDto dto) {
        return null;
    }

    @Override
    public ResponseEntity<LivroResponseDto> atualizarUmLivro(Long id, LivroAtualizacoesDto atualizacoes) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deletar(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Page<LivroResponseDto>> retornarTodosLivrosCadastrados(Pageable pageable) {
        return null;
    }

    @Override
    public ResponseEntity<LivroResponseDto> buscarUmLivroPorId(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<LivroResponseDto> buscarLivroPeloTitulo(String titulo) {
        return null;
    }

    @Override
    public ResponseEntity<Page<LivroResponseDto>> retornarLivrosCadastradosPorCategoria(String categoria, Pageable pageable) {
        return null;
    }

    @Override
    public ResponseEntity<Page<LivroResponseDto>> retornarLivrosCadastradosPorAutor(String autor, Pageable pageable) {
        return null;
    }
}