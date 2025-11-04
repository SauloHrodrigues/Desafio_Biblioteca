package com.db.projeto.gerenciamento_de_biblioteca.controller;

import com.db.projeto.gerenciamento_de_biblioteca.controller.intefaces_swagger.AutorSwaggerI;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AtualizacaoAutorDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AutorResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.NovoAutorDto;
import com.db.projeto.gerenciamento_de_biblioteca.service.AutorServiceI;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/autores")
public class AutorController implements AutorSwaggerI {

    private final AutorServiceI serviceI;

    @Override
    @PostMapping
    public ResponseEntity<AutorResponseDto> cadastrar(@Valid @RequestBody NovoAutorDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceI.cadastrar(dto));
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<AutorResponseDto>> retornarTodosAutoresCadastrados(@PageableDefault(size = 10, sort = {"nome"}) Pageable pageable) {
        var reposta = serviceI.retornarTodosAutoresCadastrados(pageable);
        return ResponseEntity.ok(reposta);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDto> buscarUmAutorPorId(@PathVariable Long id) {
        return ResponseEntity.ok(serviceI.buscarUmAutorPorId(id));
    }

    @Override
    @GetMapping("/nome")
    public ResponseEntity<List<AutorResponseDto>> buscarAutorPeloNome(@RequestParam String nome) {
        return ResponseEntity.ok(serviceI.buscarAutorPeloNome(nome));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<AutorResponseDto> atualizarUmAutor(@PathVariable Long id, @Valid @RequestBody AtualizacaoAutorDto atualizacoes) {
        var autorAtualizado = serviceI.atualizarUmAutor(id,atualizacoes);
        return ResponseEntity.ok(autorAtualizado);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagar(@PathVariable Long id) {
        serviceI.apagar(id);
        return ResponseEntity.noContent().build();
    }
}