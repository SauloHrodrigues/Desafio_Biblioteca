package com.db.projeto.gerenciamento_de_biblioteca.controller.intefaces_swagger;

import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.NovoLivroDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Livro", description = "Endpoints para gerenciar livros")
public interface LivroSwaggerI {

    @Operation(summary = "Cadastra um novo livro")
    @ApiResponse(responseCode = "201", description = "Livro cadastrado com sucesso.",
            content = @Content(schema = @Schema(implementation = LivroResponseDto.class)))
    ResponseEntity<LivroResponseDto> cadastrar(@Valid @RequestBody NovoLivroDto dto);

    @Operation(summary = "Atualiza um livro existente no banco")
    @ApiResponse(responseCode = "200", description = "Retorna o livro atualizado no banco.",
            content = @Content(schema = @Schema(implementation = LivroResponseDto.class)))
    ResponseEntity<LivroResponseDto> atualizarUmLivro(
            @Parameter(description = "ID do livro a ser atualizado", example = "42")
            @PathVariable Long id,
            @Valid @RequestBody LivroAtualizacoesDto atualizacoes);

    @Operation(summary = "Exclui um livro existente no banco")
    @ApiResponse(responseCode = "204", description = "Livro excluído com sucesso.")
    @ApiResponse(responseCode = "404", description = "Livro não encontrado.")
    ResponseEntity<Void> deletar(
            @Parameter(description = "ID do livro", example = "2")
            @PathVariable Long id);

    @Operation(summary = "Lista todos os livros cadastrados no banco")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LivroResponseDto.class))
            ))
    ResponseEntity<Page<LivroResponseDto>> retornarTodosLivrosCadastrados(
            @Parameter(description = "Parâmetros de paginação e ordenação")
            @PageableDefault(size = 10, sort = {"titulo"}) Pageable pageable);

    @Operation(summary = "Busca um livro pelo ID")
    @ApiResponse(responseCode = "200", description = "Retorna um livro buscado.",
            content = @Content(schema = @Schema(implementation = LivroResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Livro não encontrado.")
    ResponseEntity<LivroResponseDto> buscarUmLivroPorId(
            @Parameter(description = "ID do livro a ser buscado", example = "42")
            @PathVariable Long id);

    @Operation(summary = "Busca um livro pelo título")
    @ApiResponse(responseCode = "200", description = "Retorna o livro buscado.",
            content = @Content(schema = @Schema(implementation = LivroResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Livro não encontrado.")
    @GetMapping("/titulo/{titulo}")
    ResponseEntity<LivroResponseDto> buscarLivroPeloTitulo(
            @Parameter(description = "Título do livro a ser buscado", example = "Senhor dos Anéis")
            @PathVariable String titulo);

    @Operation(summary = "Lista todos os livros de determinada categoria cadastrados no banco")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LivroResponseDto.class))
            ))
    ResponseEntity<Page<LivroResponseDto>> retornarLivrosCadastradosPorCategoria(
            @Parameter(description = "Categoria dos livros a serem listados")
            @PathVariable String categoria,
            @Parameter(description = "Parâmetros de paginação e ordenação")
            @PageableDefault(size = 10, sort = {"titulo"}) Pageable pageable);

    @Operation(summary = "Lista todos os livros de determinado autor cadastrados no banco")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LivroResponseDto.class))
            ))
    ResponseEntity<Page<LivroResponseDto>> retornarLivrosCadastradosPorAutor(
            @Parameter(description = "Nome do autor dos livros a serem listados")
            @PathVariable String autor,
            @Parameter(description = "Parâmetros de paginação e ordenação")
            @PageableDefault(size = 10, sort = {"titulo"}) Pageable pageable);
}