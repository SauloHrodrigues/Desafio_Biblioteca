package com.db.projeto.gerenciamento_de_biblioteca.service;

import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AtualizacaoAutorDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AutorResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.NovoAutorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AutorServiceI {
    AutorResponseDto cadastrar(NovoAutorDto dto);
    Page<AutorResponseDto> retornarTodosAutoresCadastrados(Pageable pageable);
    AutorResponseDto buscarUmAutorPorId(Long id);
    AutorResponseDto buscarAutorPeloNome(String nome);
    AutorResponseDto atualizarUmAutor(Long id, AtualizacaoAutorDto atualizacoes);
    void apagar(Long id);
}