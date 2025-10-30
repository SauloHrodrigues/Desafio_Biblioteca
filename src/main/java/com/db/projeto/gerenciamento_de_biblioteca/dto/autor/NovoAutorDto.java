package com.db.projeto.gerenciamento_de_biblioteca.dto.autor;

import com.db.projeto.gerenciamento_de_biblioteca.enuns.Sexo;
import java.time.LocalDate;

public record NovoAutorDto(
      String nome,
      LocalDate dataDeNascimento,
      String cpf,
      Sexo sexo
) {
}
