package com.db.projeto.gerenciamento_de_biblioteca.service;

import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.LocatarioAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.LocatarioResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.NovoLocatarioDto;
import com.db.projeto.gerenciamento_de_biblioteca.service.implementacoes.LocatarioServiceImpl;

public interface LocatarioServiceI {

    LocatarioResponseDto cadastrar(NovoLocatarioDto dto);
    LocatarioResponseDto atualizar(Long id, LocatarioAtualizacoesDto dto);
}