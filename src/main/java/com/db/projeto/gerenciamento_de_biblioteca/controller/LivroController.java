package com.db.projeto.gerenciamento_de_biblioteca.controller;

import com.db.projeto.gerenciamento_de_biblioteca.controller.intefaces_swagger.LivroSwaggerI;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.NovoLivroDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.CategoriaDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.service.LivroServiceI;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/livros")
public class LivroController  { //implements LivroSwaggerI

    private final LivroServiceI serviceI;
//    @Override
    @PostMapping
    public ResponseEntity<LivroResponseDto> cadastrar(@RequestBody @Valid NovoLivroDto dto) {
        LivroResponseDto responseDto = serviceI.cadastrar(dto);
        return ResponseEntity.ok(responseDto);
    }

//    @Override
    @PutMapping("/{id}")
    public ResponseEntity<LivroResponseDto> atualizarUmLivro(@PathVariable Long id,@RequestBody LivroAtualizacoesDto atualizacoes) {
        var atualizado = serviceI.atualizar(id, atualizacoes);
        return ResponseEntity.ok(atualizado);
    }

//    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        serviceI.apagar(id);
        return ResponseEntity.noContent().build();
    }

//    @Override
    @GetMapping("/status")
    public ResponseEntity<Page<LivroResponseDto>> retornarTodosLivrosCadastrados(@RequestParam int status, Pageable pageable) {
        var todosOsLivros = serviceI.listarTodos(status,pageable);
        return ResponseEntity.ok(todosOsLivros);
    }
//    @Override
    @GetMapping()
    public ResponseEntity<Page<LivroResponseDto>> retornarTodosLivrosCadastrados(Pageable pageable) {
        var todosOsLivros = serviceI.listarTodos(pageable);
        return ResponseEntity.ok(todosOsLivros);
    }

//    @Override
    @GetMapping("/{id}")
    public ResponseEntity<LivroResponseDto> buscarUmLivroPorId(@PathVariable Long id) {
        var resposta = serviceI.buscarPorId(id);
        return ResponseEntity.ok(resposta);
    }

//    @Override
    @GetMapping("/titulo")
    public ResponseEntity<Page<LivroResponseDto>> buscarLivroPeloTitulo(@RequestParam String titulo, Pageable pageable) {
        var resposta = serviceI.buscarPorTitulo(titulo,pageable);
        return ResponseEntity.ok(resposta);
    }

//    @Override
    @GetMapping("/categoria")
    public ResponseEntity<Page<LivroResponseDto>> retornarLivrosCadastradosPorCategoria(@RequestParam CategoriaDoLivro categoria, Pageable pageable) {
        var resposta = serviceI.buscarPorCategoria(categoria,pageable);
        return ResponseEntity.ok(resposta);
    }

//    @Override
    @GetMapping("/autor/{idAutor}")
    public ResponseEntity<Page<LivroResponseDto>> retornarLivrosCadastradosPorAutor(@PathVariable Long idAutor, Pageable pageable) {
        var resposta = serviceI.buscarPorAutor(idAutor,pageable);
        return ResponseEntity.ok(resposta);
    }
}