package com.db.projeto.gerenciamento_de_biblioteca.service;

import com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel.AluguelResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel.NovoAluguelDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.LocatarioAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.LocatarioResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.NovoLocatarioDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AluguelServiceI {

    AluguelResponseDto cadastrar(NovoAluguelDto dto);
    Page<AluguelResponseDto> listarTodos(Pageable pageable);
    Page<AluguelResponseDto> listarAlugueis(String statusDoAluguel,Pageable pageable);
    AluguelResponseDto buscarPorId(Long id);
    AluguelResponseDto devolverAluguel(Long id);
}