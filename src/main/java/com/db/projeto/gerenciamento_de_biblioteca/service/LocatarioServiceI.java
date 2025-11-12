package com.db.projeto.gerenciamento_de_biblioteca.service;

import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.LocatarioAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.LocatarioResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.NovoLocatarioDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LocatarioServiceI {

    LocatarioResponseDto cadastrar(NovoLocatarioDto dto);
    Page<LocatarioResponseDto> listarTodos(Pageable pageable);
    LocatarioResponseDto buscarPorId(Long id);
    LocatarioResponseDto atualizar(Long id, LocatarioAtualizacoesDto dto);
    void apagar(Long id);
}