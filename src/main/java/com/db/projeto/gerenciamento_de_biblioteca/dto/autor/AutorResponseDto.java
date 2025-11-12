package com.db.projeto.gerenciamento_de_biblioteca.dto.autor;

import com.db.projeto.gerenciamento_de_biblioteca.enuns.Sexo;
import com.db.projeto.gerenciamento_de_biblioteca.model.Livro;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.Set;
@Schema(description = "Objeto de resposta com os dados detalhados do autor.")
public record AutorResponseDto(
        @Schema(description = "Identificador único do autor.", example = "1")
        Long  id,
        @Schema(description = "Nome completo do autor.", example = "Gustavo Cerbasi")
        String nome,
        @Schema(description = "Data de nascimento do autor.", example = "1974-05-15")
        LocalDate dataDeNascimento,
        @Schema(description = "CPF do autor.", example = "12345678900")
        String cpf,
        @Schema(description = "Gênero do autor.", example = "MASCULINO")
        Sexo sexo,
        @Schema(description = "Lista de livros associados ao autor.")
        Set<Livro> livros
) {
}
