package com.db.projeto.gerenciamento_de_biblioteca.dto.autor;

import com.db.projeto.gerenciamento_de_biblioteca.enuns.Sexo;
import com.db.projeto.gerenciamento_de_biblioteca.model.Livro;
import java.time.LocalDate;
import java.util.Set;

public record AutorResponseDto(
        Long  id,
        String nome,
        LocalDate dataDeNascimento,
        String cpf,
        Sexo sexo,
        Set<Livro> livros
) {
}
