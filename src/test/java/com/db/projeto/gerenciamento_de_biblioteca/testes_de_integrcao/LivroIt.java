package com.db.projeto.gerenciamento_de_biblioteca.testes_de_integrcao;

import com.db.projeto.gerenciamento_de_biblioteca.auxiliar.PageResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.NovoLivroDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.CategoriaDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.fixture.LivroFixture;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LivroIt {

    @Autowired
    private TestRestTemplate template;

    @Test
    @DisplayName("Deve cadastrar um livro com sucesso ")
    @Sql(scripts = {"/reset_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/cadastra_autores.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deveCadastrarUmNovoLivroComSucesso() {
        NovoLivroDto dto = LivroFixture.requestLivo01();
        ResponseEntity<LivroResponseDto> resposta = template.postForEntity("/livros", dto,
                LivroResponseDto.class);
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resposta.getBody().id()).isNotNull();
        assertThat(resposta.getBody().titulo()).isEqualTo(dto.titulo().toLowerCase());
        assertThat(resposta.getBody().categoriaDoLivro()).isEqualTo(dto.categoriaDoLivro());
        assertThat(resposta.getBody().isbn()).isEqualTo(dto.isbn());
        assertThat(resposta.getBody().publicacao()).isEqualTo(dto.publicacao());
    }

    @Test
    @DisplayName("Deve retornar uma lista de livros cadastrados no banco de dados")
    @Sql(scripts = {"/reset_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/lista_de_quatro_livros.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deveRetornarAListaDeLivrosCadastradosNoBanco() {
        ResponseEntity<PageResponseDto<LivroResponseDto>> resposta =
                template.exchange(
                        "/livros?page=0&size=5",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PageResponseDto<LivroResponseDto>>() {
                        }
                );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().getContent()).hasSize(4);
        assertThat(resposta.getBody().getTotalElements()).isEqualTo(4);
    }


    @Test
    @DisplayName("Deve atualizar os dados de um livro existente")
    @Sql(scripts = {"/reset_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/livro_id_01_para_alterar.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveAtualizarUmLivro() {
        LivroAtualizacoesDto atualizacoesDto = new LivroAtualizacoesDto("Atualizado", LocalDate.now(), null,
                CategoriaDoLivro.AUTOAJUDA);

        ResponseEntity<LivroResponseDto> resposta = template.exchange(
                "/livros/" + 1,
                HttpMethod.PUT,
                new HttpEntity<>(atualizacoesDto),
                LivroResponseDto.class
        );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().titulo()).isEqualTo(atualizacoesDto.titulo());
        assertThat(resposta.getBody().publicacao()).isEqualTo(atualizacoesDto.publicacao());
        assertThat(resposta.getBody().categoriaDoLivro()).isEqualTo(atualizacoesDto.categoriaDoLivro());
    }


    @Test
    @DisplayName("Deve buscar um livro pelo id.")
    @Sql(scripts = {"/reset_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/livro_id_01_para_alterar.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveBuscarUmLivroPeloIdNobanco(){
        ResponseEntity<LivroResponseDto> resposta = template.getForEntity("/livros/" + 1,
                LivroResponseDto.class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody().id()).isNotNull();
        assertThat(resposta.getBody().titulo()).isNotNull();
        assertThat(resposta.getBody().publicacao()).isNotNull();
        assertThat(resposta.getBody().isbn()).isNotNull();
        assertThat(resposta.getBody().categoriaDoLivro()).isNotNull();
        assertThat(resposta.getBody().idAutores()).isNotNull();
    }

    @Test
    @DisplayName("Deve retornar um livro buscado pelo título.")
    @Sql(scripts = {"/reset_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/lista_de_quatro_livros.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveBuscarUmLivroPeloTituloNoBanco() {
        String tituloBuscado = "Casais Inteligentes Enriquecem Juntos";
        ResponseEntity<PageResponseDto<LivroResponseDto>> resposta =
                template.exchange(
                        "/livros/titulo?titulo=" + tituloBuscado + "&page=0&size=5",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PageResponseDto<LivroResponseDto>>() {
                        }
                );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody().getContent().get(0).id()).isNotNull();
        assertThat(resposta.getBody().getContent().get(0).titulo()).isNotNull();
        assertThat(resposta.getBody().getContent().get(0).publicacao()).isNotNull();
        assertThat(resposta.getBody().getContent().get(0).isbn()).isNotNull();
        assertThat(resposta.getBody().getContent().get(0).categoriaDoLivro()).isNotNull();
        assertThat(resposta.getBody().getContent().get(0).idAutores()).isNotNull();
    }

    @Test
    @DisplayName("Deve retornar uma lista de livros buscados pela categoria.")
    @Sql(scripts = {"/reset_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/lista_completa_de_livros.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveBuscarLivrosPelaCategoriaNoBanco() {
        CategoriaDoLivro categoriaBuscada = CategoriaDoLivro.FANTASIA;
        ResponseEntity<PageResponseDto<LivroResponseDto>> resposta =
                template.exchange(
                        "/livros/categoria?categoria=" + categoriaBuscada + "&page=0&size=5",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PageResponseDto<LivroResponseDto>>() {
                        }
                );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody().getContent()).hasSize(3);
    }

    @Test
    @DisplayName("Deve retornar uma lista de livros buscados pelo id do autor.")
    @Sql(scripts = {"/reset_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/lista_completa_de_livros.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveBuscarLivrosPorAutorNoBanco() {
        Long idDoAutor= 3L;
        ResponseEntity<PageResponseDto<LivroResponseDto>> resposta =
                template.exchange(
                        "/livros/" + idDoAutor + "?page=0&size=10",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PageResponseDto<LivroResponseDto>>() {
                        }
                );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(resposta.getBody().getContent()).hasSize(1);
    }


}