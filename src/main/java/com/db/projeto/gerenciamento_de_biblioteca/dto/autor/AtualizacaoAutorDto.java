package com.db.projeto.gerenciamento_de_biblioteca.dto.autor;

import com.db.projeto.gerenciamento_de_biblioteca.enuns.Sexo;
import com.db.projeto.gerenciamento_de_biblioteca.model.Livro;
import java.time.LocalDate;
import java.util.Set;

public record AtualizacaoAutorDto(
        String nome,
        LocalDate dataDeNascimento,
        Sexo sexo
) {
}
