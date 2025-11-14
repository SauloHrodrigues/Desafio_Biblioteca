package com.db.projeto.gerenciamento_de_biblioteca.dto.livro;

import com.db.projeto.gerenciamento_de_biblioteca.enuns.CategoriaDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.StatusDoLivro;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Objeto de saída representando um livro cadastrado.")
public record LivroResponseDto(
        @Schema(description = "ID único do livro no banco", example = "42")
        Long id,
        @Schema(description = "Titulo do livro cadastrado", example = "Codigo Penal")
        String titulo,
        @Schema(description = "Data da publicação do livro, no formato ano-mes-dia.", example = "2020-05-15")
        LocalDate publicacao,
        @Schema(description = "identificador único de livros no mundo.", example = "978-85-333-0227-9")
        String isbn,
        @Schema(description = "Categoria do livro.", example = "Contos")
        CategoriaDoLivro categoriaDoLivro,

        @Schema(description = "Status do livro.", example = "Disponível")
        StatusDoLivro status,
        @Schema(description = "Identificadores dos autores do livro.", example = "8, 5")
        List<Long> idAutores
) {
}
