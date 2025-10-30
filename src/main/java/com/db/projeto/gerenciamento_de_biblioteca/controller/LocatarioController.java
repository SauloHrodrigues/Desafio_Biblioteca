package com.db.projeto.gerenciamento_de_biblioteca.controller;

import com.db.projeto.gerenciamento_de_biblioteca.controller.intefaces_swagger.LocatarioSwaggerI;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.LocatarioAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.LocatarioResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.NovoLocatarioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/locatarios")
public class LocatarioController implements LocatarioSwaggerI {
    @Override
    public ResponseEntity<LocatarioResponseDto> cadastrar(NovoLocatarioDto dto) {
        return null;
    }

    @Override
    public ResponseEntity<LocatarioResponseDto> atualizarUmLocatario(Long id, LocatarioAtualizacoesDto atualizacoes) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deletar(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Page<LivroResponseDto>> retornarTodosLocatariosCadastrados(Pageable pageable) {
        return null;
    }

    @Override
    public ResponseEntity<LocatarioResponseDto> buscarUmLocatarioPorId(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<Page<LivroResponseDto>> retornarTodoAlugueiDoLocatario(Long id, Pageable pageable) {
        return null;
    }
}