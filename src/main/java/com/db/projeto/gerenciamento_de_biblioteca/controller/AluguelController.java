package com.db.projeto.gerenciamento_de_biblioteca.controller;

import com.db.projeto.gerenciamento_de_biblioteca.controller.intefaces_swagger.AluguelSwaggerI;
import com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel.AluguelResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel.NovoAluguelDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/alugueis")
public class AluguelController implements AluguelSwaggerI {
    @Override
    @PostMapping
    public ResponseEntity<AluguelResponseDto> cadastrar(@RequestBody NovoAluguelDto dto) {
        return null;
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<AluguelResponseDto>> retornarTodosAlugueisCadastrados(Pageable pageable) {
        return null;
    }

    @Override
    public ResponseEntity<Page<LivroResponseDto>> buscarLivrosDeDeterminadoAluguelPorId(Long id) {
        return null;
    }
}