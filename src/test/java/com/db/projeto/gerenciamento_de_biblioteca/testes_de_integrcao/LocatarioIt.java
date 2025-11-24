package com.db.projeto.gerenciamento_de_biblioteca.testes_de_integrcao;

import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.LocatarioAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.LocatarioResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.NovoLocatarioDto;
import com.db.projeto.gerenciamento_de_biblioteca.fixture.LocatarioFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocatarioIt {
    @Autowired
    private TestRestTemplate template;

    @Test
    @DisplayName("Deve cadastrar um locatario com sucesso ")
    @Sql(scripts = {"/reset_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void deveCadastrarUmNovoLocatarioComSucesso() {
        NovoLocatarioDto requestDto = LocatarioFixture.request();

        ResponseEntity<LocatarioResponseDto> resposta = template.postForEntity("/locatarios", requestDto,
                LocatarioResponseDto.class);
        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(resposta.getBody().id()).isNotNull();
        assertThat(resposta.getBody().nome()).isEqualTo(requestDto.nome().toLowerCase());
        assertThat(resposta.getBody().cpf()).isEqualTo(requestDto.cpf());
        assertThat(resposta.getBody().email()).isEqualTo(requestDto.email());

    }

    @Test
    @DisplayName("Deve atualizar os dados de um locatário existente")
    @Sql(scripts = {"/reset_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/lista_de_locatarios.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveAtualizarUmLocatario() {
        LocatarioAtualizacoesDto atualizacoesDto = new LocatarioAtualizacoesDto("Locatario Atualizado",null,
                null,"99999999999","novo@gmail.com");

        ResponseEntity<LocatarioResponseDto> resposta = template.exchange(
                "/locatarios/" + 1,
                HttpMethod.PUT,
                new HttpEntity<>(atualizacoesDto),
                LocatarioResponseDto.class
        );

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().id()).isNotNull();
        assertThat(resposta.getBody().nome()).isEqualTo(atualizacoesDto.nome());
        assertThat(resposta.getBody().dataDeNascimento()).isNotNull();
        assertThat(resposta.getBody().sexo()).isNotNull();
        assertThat(resposta.getBody().telefone()).isEqualTo(atualizacoesDto.telefone());
    }

//    @Test
//    @DisplayName("Deve apagar um Locatário existente")
//    @Sql(scripts = {"/reset_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = {"/lista_de_locatarios.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    void deveApagarAnimalPorId () {
//        Long idDoLocatario = 3L;
//        ResponseEntity<Void> resposta = template.exchange(
//                "/locatarios/" + idDoLocatario,
//                HttpMethod.DELETE,
//                HttpEntity.EMPTY,
//                Void.class
//        );
//
//        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//    }

    @Test
    @DisplayName("Deve buscar um locatário pelo id.")
    @Sql(scripts = {"/reset_banco.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"/lista_de_locatarios.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deveBuscarUmLocatarioPeloIdNobanco(){
        Long idDoLocatario = 1L;
        ResponseEntity<LocatarioResponseDto> resposta = template.getForEntity("/locatarios/" + idDoLocatario,
                LocatarioResponseDto.class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().id()).isEqualTo(idDoLocatario);
        assertThat(resposta.getBody().nome()).isNotNull();
        assertThat(resposta.getBody().dataDeNascimento()).isNotNull();
        assertThat(resposta.getBody().sexo()).isNotNull();
        assertThat(resposta.getBody().telefone()).isNotNull();
    }
}