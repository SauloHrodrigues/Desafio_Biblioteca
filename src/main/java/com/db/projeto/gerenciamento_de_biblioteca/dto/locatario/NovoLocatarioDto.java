package com.db.projeto.gerenciamento_de_biblioteca.dto.locatario;

import com.db.projeto.gerenciamento_de_biblioteca.enuns.Sexo;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import org.hibernate.validator.constraints.br.CPF;

@Schema(description = "Objeto para receber um novo locatário.")
public record NovoLocatarioDto(
        @Schema(description = "Nome do locatario.", example = "José roberto")
        @NotBlank(message = "O nome do locatário é campo de preenchimento obrigatório.")
        String nome,
        @Schema(description = "Data de nascimento do locatário.", example = "1974-05-15")
        @NotNull(message = "A data de nascimento é campo de preenchimento obrigatório.")
        @PastOrPresent(message = "A data não pode ser no futuro")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dataDeNascimento,
        @Schema(description = "CPF do locatário.", example = "123.456.789-09")
        @NotBlank(message = "O CPF é campo de preenchimento obrigatório.")
        @CPF(message = "CPF inválido.Por favor cadastre um CPF com 11 digitos.")
        String cpf,
        @Schema(description = "O sexo do locatário.", example = "MASCULINO / FEMININO")
        @NotNull(message = "O Sexo do locatário é campo de preenchimento obrigatório.")
        Sexo sexo,
        @Schema(description = "O telefone do locatario.", example = "(99) 99999-9999")
        @NotBlank(message = "O telefone do locatário é campo de preenchimento obrigatório.")
        String telefone,
        @Schema(description = "O e-mail do locatário.", example = "email@provedor.com")
        @NotBlank(message = "O e-mail do locatário é campo de preenchimento obrigatório.")
        @Email(message = "E-mail inválido.")
        String email
) {
    @JsonCreator
    public NovoLocatarioDto {
        if (cpf != null) {
            cpf = cpf.replaceAll("\\D", "");
        }

        if (telefone != null) {
            telefone = telefone.replaceAll("\\D", "");
        }
    }
}