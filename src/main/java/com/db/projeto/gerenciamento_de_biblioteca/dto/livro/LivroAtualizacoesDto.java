package com.db.projeto.gerenciamento_de_biblioteca.dto.livro;

import com.db.projeto.gerenciamento_de_biblioteca.enuns.CategoriaDoLivro;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

@Schema(description = "Objeto para receber atualizações um livro.")
public record LivroAtualizacoesDto(
        @Schema(description = "Título do livro.", example = "Senhor dos Anéis")
        String titulo,

        @Schema(description = "Data de publicação do livro.", example = "1954-07-29")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate publicacao,

        @Schema(description = "ISBN do livro (13 dígitos).", example = "978-85-333-1234-5")
        @Pattern(
                regexp = "^(?:\\D*\\d){13}\\D*$",
                message = "SBN inválido. Por favor cadastre um livro com ISBN de 13 dígito."
        )
        String isbn,

        @Schema(description = "Categoria do livro.", example = "FICCAO_CIENTIFICA")
        CategoriaDoLivro categoriaDoLivro
) {}
