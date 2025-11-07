package com.db.projeto.gerenciamento_de_biblioteca.controller.intefaces_swagger;

import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.NovoLivroDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.CategoriaDoLivro;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Livro", description = "Endpoints para gerenciar livros")
public interface LivroSwaggerI {

    @Operation(summary = "Cadastra um novo livro")
    @ApiResponse(responseCode = "201", description = "Livro cadastrado com sucesso.",
            content = @Content(schema = @Schema(implementation = LivroResponseDto.class)))
    ResponseEntity<LivroResponseDto> cadastrar(NovoLivroDto dto);
//    ************************************************************************************************

    @Operation(summary = "Atualiza um livro existente no banco")
    @ApiResponse(responseCode = "200", description = "Retorna o livro atualizado no banco.",
            content = @Content(schema = @Schema(implementation = LivroResponseDto.class)))
    ResponseEntity<LivroResponseDto> atualizarUmLivro(
            @Parameter(description = "ID do livro a ser atualizado", example = "42")
            @PathVariable Long id,
            @Valid @RequestBody LivroAtualizacoesDto atualizacoes);

//     ************************************************************************************************

    @Operation(summary = "Exclui um livro existente no banco")
    @ApiResponse(responseCode = "204", description = "Livro excluído com sucesso.")
    @ApiResponse(responseCode = "404", description = "Livro não encontrado.")
    ResponseEntity<Void> deletar(
            @Parameter(description = "ID do livro", example = "2")
            @PathVariable Long id);

    //    ************************************************************************************************

    @Operation(summary = "Lista todos os livros cadastrados no banco")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LivroResponseDto.class))
            ))
    ResponseEntity<Page<LivroResponseDto>> retornarTodosLivrosCadastrados(
            @Parameter(description = "Parâmetros de paginação e ordenação")
            @PageableDefault(size = 10, sort = {"titulo"}) Pageable pageable);

    //    ************************************************************************************************

    @Operation(summary = "Lista todo os livros. Se parametro \'0\' retorna os disponiveis, se \'1\' retorna os indisponiveis.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LivroResponseDto.class))
            ))
    ResponseEntity<Page<LivroResponseDto>> retornarTodosLivrosCadastrados(
            @Parameter(description = "Parâmetros de paginação e ordenação")
            @RequestParam int status,
            @PageableDefault(size = 10, sort = {"titulo"}) Pageable pageable);

//    **********************************************************************************

    @Operation(summary = "Busca um livro pelo ID")
    @ApiResponse(responseCode = "200", description = "Retorna um livro buscado.",
            content = @Content(schema = @Schema(implementation = LivroResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Livro não encontrado.")
    ResponseEntity<LivroResponseDto> buscarUmLivroPorId(
            @Parameter(description = "ID do livro a ser buscado", example = "42")
            @PathVariable Long id);

//    ********************************************************************************************

    @Operation(summary = "Busca um livro pelo título")
    @ApiResponse(responseCode = "200", description = "Retorna o livro buscado.",
            content = @Content(schema = @Schema(implementation = LivroResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Livro não encontrado.")
    @GetMapping("/titulo")
    ResponseEntity<Page<LivroResponseDto>> buscarLivroPeloTitulo(
            @Parameter(description = "Título do livro a ser buscado", example = "Senhor dos Anéis")
            @RequestParam String titulo,
            @PageableDefault(size = 10, sort = {"titulo"}) Pageable pageable);

//    **********************************************************************************

    @Operation(summary = "Lista todos os livros de determinada categoria, informada pelo usuario")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LivroResponseDto.class))
            ))
    ResponseEntity<Page<LivroResponseDto>> retornarLivrosCadastradosPorCategoria(
            @Parameter(description = "CategoriaDoLivro dos livros a serem listados")
            @RequestParam CategoriaDoLivro categoriaDoLivro,
            @PageableDefault(size = 10, sort = {"titulo"}) Pageable pageable);

//    ************************************************************************************

    @Operation(summary = "Lista todos os livros de determinado autor, pesquisado por id.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso.",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LivroResponseDto.class))
            ))
    ResponseEntity<Page<LivroResponseDto>> retornarLivrosCadastradosPorAutor(
            @Parameter(description = "Id do autor dos livros a serem listados")
            @PathVariable Long idAutor,
            @Parameter(description = "Parâmetros de paginação e ordenação")
            @PageableDefault(size = 10, sort = {"titulo"}) Pageable pageable);
}