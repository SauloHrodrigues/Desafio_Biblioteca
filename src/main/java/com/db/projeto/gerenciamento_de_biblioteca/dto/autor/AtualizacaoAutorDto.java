package com.db.projeto.gerenciamento_de_biblioteca.dto.autor;

import com.db.projeto.gerenciamento_de_biblioteca.enuns.Sexo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Schema(description = "Objeto para receber um novo autor.")
public record AtualizacaoAutorDto(
        @Schema(description = "Nome do autor.", example = "José roberto")
        String nome,
        @Schema(description = "Data de nascimento do autor.", example = "1974-05-15")
        @PastOrPresent(message = "A data não pode ser no futuro")
        LocalDate dataDeNascimento,
        @Schema(description = "Genero da pessoa.", example = "MASCULINO / FEMININO")
        Sexo sexo
) {
}
