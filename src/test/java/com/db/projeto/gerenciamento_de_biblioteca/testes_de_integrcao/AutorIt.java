package com.db.projeto.gerenciamento_de_biblioteca.testes_de_integrcao;

import com.db.projeto.gerenciamento_de_biblioteca.auxiliar.PageResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AtualizacaoAutorDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AutorResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.NovoAutorDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.NovoLivroDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.CategoriaDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.Sexo;
import com.db.projeto.gerenciamento_de_biblioteca.fixture.AutorFixture;
import com.db.projeto.gerenciamento_de_biblioteca.fixture.LivroFixture;
import java.time.LocalDate;
import java.util.List;
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
public class AutorIt {

    @Autowired
    private TestRestTemplate template;

    @Test
    @DisplayName("Deve cadastrar um autor com sucesso ")
    @Sql(scripts = {"/reset_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deveCadastrarUmNovoAutorComSucesso() {
        NovoAutorDto novoAutorDto = AutorFixture.requestDto01();
        ResponseEntity<AutorResponseDto> resposta = template.postForEntity("/autores", novoAutorDto,
                AutorResponseDto.class);
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resposta.getBody().id()).isNotNull();
        assertThat(resposta.getBody().nome()).isEqualTo(novoAutorDto.nome().toLowerCase());
        assertThat(resposta.getBody().cpf()).isEqualTo(novoAutorDto.cpf());
        assertThat(resposta.getBody().dataDeNascimento()).isEqualTo(novoAutorDto.dataDeNascimento());
        assertThat(resposta.getBody().sexo()).isEqualTo(novoAutorDto.sexo());

    }

    @Test
    @DisplayName("Deve retornar uma lista de autores cadastrados no banco de dados")
    @Sql(scripts = {"/reset_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/lista_de_autores.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deveRetornarAListaDeLivrosCadastradosNoBanco() {
        ResponseEntity<PageResponseDto<AutorResponseDto>> resposta =
                template.exchange(
                        "/autores?page=0&size=5",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<PageResponseDto<AutorResponseDto>>() {
                        }
                );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().getContent()).hasSize(5);
        assertThat(resposta.getBody().getTotalElements()).isEqualTo(20);
    }

    @Test
    @DisplayName("Deve buscar um autor pelo id.")
    @Sql(scripts = {"/reset_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/lista_de_autores.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveBuscarUmLivroPeloIdNobanco(){
        Long idDoAutor = 1L;
        ResponseEntity<AutorResponseDto> resposta = template.getForEntity("/autores/" + idDoAutor,
                AutorResponseDto.class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody().id()).isNotNull();
        assertThat(resposta.getBody().nome()).isNotNull();
        assertThat(resposta.getBody().cpf()).isNotNull();
        assertThat(resposta.getBody().sexo()).isNotNull();
        assertThat(resposta.getBody().dataDeNascimento()).isNotNull();
    }

    @Test
    @DisplayName("Deve retornar um autor buscado pelo nome.")
    @Sql(scripts = {"/reset_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/lista_de_autores.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveBuscarUmLivroPeloTituloNoBanco() {
        String nomeBuscado = "autor 1";
        ResponseEntity<List<AutorResponseDto>> resposta =
                template.exchange(
                        "/autores/nome?nome=" + nomeBuscado + "&page=0&size=5",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<AutorResponseDto>>() {}
                );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody().get(0).id()).isNotNull();
        assertThat(resposta.getBody().size()).isEqualTo(11);
   }

    @Test
    @DisplayName("Deve atualizar os dados de um autor existente")
    @Sql(scripts = {"/reset_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/lista_de_autores.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveAtualizarUmAutor() {
        AtualizacaoAutorDto atualizacoesDto = new AtualizacaoAutorDto("Autor Atualizado",
                null, Sexo.FEMININO);

        ResponseEntity<AutorResponseDto> resposta = template.exchange(
                "/autores/" + 1,
                HttpMethod.PUT,
                new HttpEntity<>(atualizacoesDto),
                AutorResponseDto.class
        );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().nome()).isEqualTo(atualizacoesDto.nome());
        assertThat(resposta.getBody().dataDeNascimento()).isNotEqualTo(atualizacoesDto.dataDeNascimento());

    }

    @Test
    @DisplayName("Deve apagar um autor existente")
    @Sql(scripts = {"/reset_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/lista_de_autores.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveApagarAnimalPorId () {
        Long idDoAutor = 3L;
        ResponseEntity<Void> resposta = template.exchange(
                "/autores/" + idDoAutor,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                Void.class
        );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}