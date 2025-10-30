package com.db.projeto.gerenciamento_de_biblioteca.controller.intefaces_swagger;

import com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel.AluguelResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel.NovoAluguelDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AtualizacaoAutorDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AutorResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.NovoAutorDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroResponseDto;
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

@Tag(name = "Aluguél", description = "Endpoints para gerenciar aluguéis")
public interface AluguelSwaggerI {

    @Operation(summary = "Recebe o cadastro de um novo aluguel.")
    @ApiResponse(responseCode = "200", description = "Cadastra um um novo aluguel com sucesso.",
            content = @Content(schema = @Schema(implementation = AutorResponseDto.class)))
    ResponseEntity<AluguelResponseDto> cadastrar(NovoAluguelDto dto);


    @Operation(summary = "Lista todos os aluguéis cadastrados no banco")
    @ApiResponse(
            responseCode = "200",
            description = "Retorna todos os aluguéis cadastrados no banco com sucesso.",
            content = @Content(schema = @Schema(implementation = AluguelResponseDto.class))
    )
    ResponseEntity<Page<AluguelResponseDto>> retornarTodosAlugueisCadastrados(
            @Parameter(description = "Parâmetros de paginação e ordenação")
            @PageableDefault(size = 10, sort = {"retirada"}) Pageable pageable);


    @Operation(summary = "Busca todos os livros de um aluguel pelo ID")
    @ApiResponse(
            responseCode = "200",
            description = "Retorna uma lista de livros do aluguel informado.",
            content = @Content(schema = @Schema(implementation = LivroResponseDto.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Aluguel não encontrado."
    )
    ResponseEntity<Page<LivroResponseDto>> buscarLivrosDeDeterminadoAluguelPorId(
            @Parameter(description = "ID do aluguel a ser buscado", example = "42")
            @PathVariable Long id);
}