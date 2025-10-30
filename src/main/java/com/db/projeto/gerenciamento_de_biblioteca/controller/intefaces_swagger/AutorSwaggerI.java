package com.db.projeto.gerenciamento_de_biblioteca.controller.intefaces_swagger;

import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AtualizacaoAutorDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AutorResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.NovoAutorDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

@Tag(name = "Autor", description = "Endpoints para gerenciar clientes")
public interface AutorSwaggerI {

    @Operation(summary = "Recebe o cadastro de um novo autor.")
    @ApiResponse(responseCode = "200", description = "Cadastra um um novo autor com sucesso.",
            content = @Content(schema = @Schema(implementation = AutorResponseDto.class)))
    ResponseEntity<AutorResponseDto> cadastrar(NovoAutorDto dto);

    @Operation(summary = "Lista todos os autores cadastrados no banco")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.",
            content = @Content(schema = @Schema(implementation = AutorResponseDto.class)))
    ResponseEntity<Page<AutorResponseDto>> retornarTodosAutoresCadastrados(
            @Parameter(description = "Parâmetros de paginação e ordenação")
            @PageableDefault(size = 10, sort = {"nome"}) Pageable pageable);

    @Operation(summary = "Busca um autor pelo ID")
    @ApiResponse(responseCode = "200", description = "Retorna o autor buscado.",
            content = @Content(schema = @Schema(implementation = AutorResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Autor não encontrado.")
    ResponseEntity<AutorResponseDto> buscarUmAnimalPorId(
            @Parameter(description = "ID do autor a ser buscado", example = "42")
            @PathVariable Long id);

    @Operation(summary = "Busca um autor pelo nome")
    @ApiResponse(responseCode = "200", description = "Retorna o autor buscado.",
            content = @Content(schema = @Schema(implementation = AutorResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Autor não encontrado.")
    ResponseEntity<AutorResponseDto> buscarAutorPeloNome(
            @Parameter(description = "nome do autor a ser buscado", example = "maria antonieta")
            @PathVariable String nome);

    @Operation(summary = "Atualiza um autor existente no banco")
    @ApiResponse(responseCode = "200", description = "Retorna o autor atualizado no banco.",
            content = @Content(schema = @Schema(implementation = AutorResponseDto.class)))
    ResponseEntity<AutorResponseDto> atualizarUmAutor(
            @Parameter(description = "ID do autor a ser atualizado", example = "42")
            @PathVariable Long id,
            @Valid @RequestBody AtualizacaoAutorDto atualizacoes);

    @Operation(summary = "Exclui um autor existente no banco")
    @ApiResponse(responseCode = "204", description = "Autor excluído com sucesso.")
    @ApiResponse(responseCode = "404", description = "Autor não encontrado.")
    ResponseEntity<Void> apagar(@Parameter(description = "ID do Autor", example = "42")
                                @PathVariable Long id);

}