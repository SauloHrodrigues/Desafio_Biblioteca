package com.db.projeto.gerenciamento_de_biblioteca.dto.livro;

import com.db.projeto.gerenciamento_de_biblioteca.enuns.CategoriaDoLivro;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@Schema(description = "Objeto para receber um novo livro.")
public record NovoLivroDto(
        @Schema(description = "Título do livro.", example = "Senhor dos Anéis")
        @NotBlank(message = "O título do livro é campo de preenchimento obrigatório.")
        String titulo,

        @Schema(description = "Data de publicação do livro.", example = "1954-07-29")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @NotNull(message = "A data de publicação é campo de preenchimento obrigatório.")
        LocalDate publicacao,

        @Schema(description = "ISBN do livro (13 dígitos).", example = "978-85-333-1234-5")
        @NotBlank(message = "O ISBN é campo de preenchimento obrigatório.")
        @Pattern(
                regexp = "^(?:\\D*\\d){13}\\D*$",
                message = "SBN inválido. Por favor cadastre um livro com ISBN de 13 dígito."
        )
        String isbn,

        @Schema(description = "Categoria do livro.", example = "FICCAO_CIENTIFICA")
        @NotNull(message = "A categoria do livro é campo de preenchimento obrigatório.")
        CategoriaDoLivro categoriaDoLivro,

        @Schema(description = "Lista com os IDs dos autores do livro.", example = "[1, 2]")
        @NotNull(message = "A lista de autores não pode ser nula.")
        @NotEmpty(message = "É necessário informar pelo menos um autor.")
        List<@Positive(message = "O ID do autor deve ser positivo.") Long> idAutores

) {}
