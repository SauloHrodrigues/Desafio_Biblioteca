package com.db.projeto.gerenciamento_de_biblioteca.dto.autor;

import com.db.projeto.gerenciamento_de_biblioteca.enuns.GeneroDaPessoa;
import java.time.LocalDate;

public record AtualizacaoAutorDto(
        String nome,
        LocalDate dataDeNascimento,
        GeneroDaPessoa generoDaPessoa
) {
}
