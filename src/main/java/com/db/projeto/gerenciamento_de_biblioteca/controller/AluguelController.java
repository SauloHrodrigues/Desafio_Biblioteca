package com.db.projeto.gerenciamento_de_biblioteca.controller;

import com.db.projeto.gerenciamento_de_biblioteca.controller.intefaces_swagger.AluguelSwaggerI;
import com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel.AluguelResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel.NovoAluguelDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.service.AluguelServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/alugueis")
public class AluguelController {//implements AluguelSwaggerI

    private final AluguelServiceI service;

    //    @Override
    @PostMapping
    public ResponseEntity<AluguelResponseDto> cadastrar(@RequestBody NovoAluguelDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(dto));
    }

    //    @Override
    @GetMapping
    public ResponseEntity<Page<AluguelResponseDto>> retornarTodosAlugueisCadastrados(Pageable pageable) {
        var resposta = service.listarTodos(pageable);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/filtrar")
    public ResponseEntity<Page<AluguelResponseDto>> retornarAlugueisCadastrados(@RequestParam String status, Pageable pageable) {
        var resposta = service.listarAlugueis(status, pageable);
        return ResponseEntity.ok(resposta);
    }

    //    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AluguelResponseDto> buscarLivrosDeDeterminadoAluguelPorId(@PathVariable Long id) {
        var resposta = service.buscarPorId(id);
        return ResponseEntity.ok(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AluguelResponseDto> devolucao(@PathVariable Long id) {
        var resposta = service.devolverAluguel(id);
        return ResponseEntity.ok(resposta);
    }
}