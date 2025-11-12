package com.db.projeto.gerenciamento_de_biblioteca.dto.locatario;

import com.db.projeto.gerenciamento_de_biblioteca.enuns.Sexo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

@Schema(description = "Objeto para atualizar um locatário.")
public record LocatarioAtualizacoesDto(
        @Schema(description = "ID único do locatario no banco", example = "42")
        Long id,
        @Schema(description = "Nome do locatario.", example = "José roberto")
        String nome,

        @JsonFormat(pattern = "yyyy-MM-dd")
        @PastOrPresent(message = "A data não pode ser no futuro")
        LocalDate dataDeNascimento,

        @Schema(description = "O sexo do locatário.", example = "MASCULINO / FEMININO")
        Sexo sexo,
        @Schema(description = "O telefone do locatario.", example = "(99) 99999-9999")
        String telefone,
        @Schema(description = "O e-mail do locatário.", example = "email@provedor.com")
        @Email(message = "E-mail inválido.")
        String email
) {
}
