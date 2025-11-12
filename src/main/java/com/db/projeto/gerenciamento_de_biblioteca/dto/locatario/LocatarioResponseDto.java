package com.db.projeto.gerenciamento_de_biblioteca.dto.locatario;

import com.db.projeto.gerenciamento_de_biblioteca.enuns.Sexo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
@Schema(description = "Representa resposta à um cadastro, atualização ou consulta de um locatário.")
public record LocatarioResponseDto(
        @Schema(description = "Identificador único do locatario.", example = "1")
        Long  id,
        @Schema(description = "Nome do locatario.", example = "José roberto")
        String nome,
        @Schema(description = "Data de nascimento do locatário.", example = "1974-05-15")
        LocalDate dataDeNascimento,
        @Schema(description = "CPF do locatário.", example = "123.456.789-09")
        String cpf,
        @Schema(description = "O sexo do locatário.", example = "MASCULINO / FEMININO")
        Sexo sexo,
        @Schema(description = "O telefone do locatario.", example = "(99) 99999-9999")
        String telefone,
        @Schema(description = "O e-mail do locatário.", example = "email@provedor.com")
        String email
) {
}
