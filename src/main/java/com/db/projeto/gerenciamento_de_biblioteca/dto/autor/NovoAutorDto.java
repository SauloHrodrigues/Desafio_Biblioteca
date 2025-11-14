package com.db.projeto.gerenciamento_de_biblioteca.dto.autor;

import com.db.projeto.gerenciamento_de_biblioteca.enuns.Sexo;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import org.hibernate.validator.constraints.br.CPF;

@Schema(description = "Objeto para receber um novo autor.")
public record NovoAutorDto(
        @Schema(description = "Nome do autor.", example = "José roberto")
        @NotBlank(message = "O nome do autor é campo de preenchimento obrigatório.")
        String nome,
        @Schema(description = "Data de nascimento do autor.", example = "1974-05-15")
        @NotNull(message = "A data de nascimento é campo de preenchimento obrigatório.")
        @PastOrPresent(message = "A data não pode ser no futuro")
        LocalDate dataDeNascimento,

        @Schema(description = "CPF do autor.", example = "123.456.789-09")
        @NotBlank(message = "O CPF é campo de preenchimento obrigatório.")
        @CPF(message = "CPF inválido. Informe um CPF válido.")
        String cpf,

        @Schema(description = "Genero da pessoa.", example = "MASCULINO / FEMININO")
        @NotNull(message = "A sexo da pessoa é campo de preenchimento obrigatório.")
        Sexo sexo
) {
        @JsonCreator
        public NovoAutorDto {
                if (cpf != null) {
                        cpf = cpf.replaceAll("\\D", "");                 }
        }
}