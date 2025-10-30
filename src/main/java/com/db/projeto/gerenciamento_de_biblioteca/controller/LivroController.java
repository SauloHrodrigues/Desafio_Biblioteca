package com.db.projeto.gerenciamento_de_biblioteca.controller;

import com.db.projeto.gerenciamento_de_biblioteca.controller.intefaces_swagger.LivroSwaggerI;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.NovoLivroDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/livros")
public class LivroController implements LivroSwaggerI {

    @Override
    @PostMapping
    public ResponseEntity<LivroResponseDto> cadastrar(NovoLivroDto dto) {
        return null;
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDto> atualizarUmLivro(@PathVariable Long id,@RequestBody LivroAtualizacoesDto atualizacoes) {
        return null;
    }

    @Override
    @DeleteMapping("/id")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return null;
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<LivroResponseDto>> retornarTodosLivrosCadastrados(Pageable pageable) {
        return null;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDto> buscarUmLivroPorId(@PathVariable Long id) {
        return null;
    }

    @Override
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<LivroResponseDto> buscarLivroPeloTitulo(@PathVariable String titulo) {
        return null;
    }

    @Override
    @GetMapping("/Categoria/{categoria}")
    public ResponseEntity<Page<LivroResponseDto>> retornarLivrosCadastradosPorCategoria(@PathVariable String categoria, Pageable pageable) {
        return null;
    }

    @Override
    @GetMapping("/autor/{autor}")
    public ResponseEntity<Page<LivroResponseDto>> retornarLivrosCadastradosPorAutor(@PathVariable String autor, Pageable pageable) {
        return null;
    }
}