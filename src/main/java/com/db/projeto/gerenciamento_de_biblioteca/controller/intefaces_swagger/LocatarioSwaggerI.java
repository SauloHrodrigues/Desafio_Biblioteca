package com.db.projeto.gerenciamento_de_biblioteca.controller.intefaces_swagger;

import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.LocatarioAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.LocatarioResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.NovoLocatarioDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Locatario", description = "Endpoints para gerenciar locatarios")
public interface LocatarioSwaggerI {

    @Operation(summary = "Cadastra um novo locatario")
    @ApiResponse(responseCode = "201", description = "Locatario cadastrado com sucesso.",
            content = @Content(schema = @Schema(implementation = LivroResponseDto.class)))
    ResponseEntity<LocatarioResponseDto> cadastrar(@Valid @RequestBody NovoLocatarioDto dto);


    @Operation(summary = "Atualiza um locatario existente no banco")
    @ApiResponse(responseCode = "200", description = "Retorna o locatario atualizado no banco.",
            content = @Content(schema = @Schema(implementation = LocatarioResponseDto.class)))
    ResponseEntity<LocatarioResponseDto> atualizarUmLocatario(
            @Parameter(description = "ID do locatario a ser atualizado", example = "32")
            @PathVariable Long id,
            @Valid @RequestBody LocatarioAtualizacoesDto atualizacoes);


    @Operation(summary = "Exclui um locatário existente no banco")
    @ApiResponse(responseCode = "204", description = "Locatário excluído com sucesso.")
    @ApiResponse(responseCode = "404", description = "Locatário não encontrado.")
    ResponseEntity<Void> deletar(
            @Parameter(description = "ID do Locatário", example = "2")
            @PathVariable Long id);


    @Operation(summary = "Lista todos os locatário cadastrados no banco")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LocatarioResponseDto.class))
            ))
    ResponseEntity<Page<LivroResponseDto>> retornarTodosLocatariosCadastrados(
            @Parameter(description = "Parâmetros de paginação e ordenação")
            @PageableDefault(size = 10, sort = {"nome"}) Pageable pageable);


    @Operation(summary = "Busca um locatário pelo ID")
    @ApiResponse(responseCode = "200", description = "Retorna um locatário buscado.",
            content = @Content(schema = @Schema(implementation = LocatarioResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Locatário não encontrado.")
    ResponseEntity<LocatarioResponseDto> buscarUmLocatarioPorId(
            @Parameter(description = "ID do locatário a ser buscado", example = "5")
            @PathVariable Long id);


    @Operation(summary = "Lista todos os livros de determinado autor cadastrados no banco")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LivroResponseDto.class))
            ))
    ResponseEntity<Page<LivroResponseDto>> retornarTodosOsAlugueisDoLocatario(
            @Parameter(description = "Retorna os aluguéis do locatario")
            @PathVariable Long id,
            @Parameter(description = "Parâmetros de paginação e ordenação")
            @PageableDefault(size = 10, sort = {"titulo"}) Pageable pageable);
    }