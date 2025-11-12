package com.db.projeto.gerenciamento_de_biblioteca.controller;

import com.db.projeto.gerenciamento_de_biblioteca.controller.intefaces_swagger.LocatarioSwaggerI;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.LocatarioAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.LocatarioResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.NovoLocatarioDto;
import com.db.projeto.gerenciamento_de_biblioteca.service.LocatarioServiceI;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/locatarios")
public class LocatarioController implements LocatarioSwaggerI {

    private final LocatarioServiceI serviceI;
    @Override
    @PostMapping
    public ResponseEntity<LocatarioResponseDto> cadastrar(@Valid @RequestBody NovoLocatarioDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceI.cadastrar(dto));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<LocatarioResponseDto> atualizarUmLocatario(@PathVariable Long id,@Valid @RequestBody LocatarioAtualizacoesDto atualizacoes) {
       var atualizado = serviceI.atualizar(id, atualizacoes);
        return ResponseEntity.ok(atualizado);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        return null;
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<LivroResponseDto>> retornarTodosLocatariosCadastrados(Pageable pageable) {
        return null;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<LocatarioResponseDto> buscarUmLocatarioPorId(@PathVariable Long id) {
        return null;
    }

    @Override
    @GetMapping("/{id}/alugueis")
    public ResponseEntity<Page<LivroResponseDto>> retornarTodosOsAlugueisDoLocatario(Long id, Pageable pageable) {
        return null;
    }
}