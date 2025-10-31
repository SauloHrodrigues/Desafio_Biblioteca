package com.db.projeto.gerenciamento_de_biblioteca.service.implementacoes;

import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AtualizacaoAutorDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AutorResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.NovoAutorDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.Sexo;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.AutorJaCadastradoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.AutorNaoCadastradoException;
import com.db.projeto.gerenciamento_de_biblioteca.fixture.AutorFixture;
import com.db.projeto.gerenciamento_de_biblioteca.model.Autor;
import com.db.projeto.gerenciamento_de_biblioteca.repository.AutorRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutorServiceImplTest {

    @InjectMocks
    private AutorServiceImpl service;

    @Mock
    private AutorRepository repository;

    @Test
    @DisplayName("Deve cadastrar um novo autor com sucesso.")
    void cadastrarNovoAutorCoSucesso() {
        NovoAutorDto dto = AutorFixture.requestDto("Paulo",LocalDate.of(1978, 9, 12), Sexo.MASCULINO);
        Autor autor = AutorFixture.entity(dto);

        when(repository.findByNomeIgnoreCase(dto.nome())).thenReturn(Optional.empty());
        when(repository.save(any(Autor.class))).thenReturn(autor);

        AutorResponseDto resposta = service.cadastrar(dto);

        assertNotNull(resposta.id());
        assertEquals(autor.getNome(),resposta.nome());
        assertEquals(autor.getCpf(),resposta.cpf());
        assertEquals(autor.getSexo(),resposta.sexo());
    }

    @Test
    @DisplayName("Deve lançar excecao ao tentar cadastrar um novo autor.")
    void cadastrarNovoAutorComExcecao() {
        NovoAutorDto dto = AutorFixture.requestDto("Paulo",LocalDate.of(1978, 9, 12), Sexo.MASCULINO);
        Autor autor = AutorFixture.entity(dto);

        when(repository.findByNomeIgnoreCase(dto.nome())).thenReturn(Optional.of(autor));

        AutorJaCadastradoException exception = assertThrows(AutorJaCadastradoException.class,
                ()->{
                    service.cadastrar(dto);
                });

        assertTrue(exception.getMessage().contains(
                "O autor: "+autor.getNome().toUpperCase()+
                        " já esta cadastrado no banco."
        ));

    }

    @Test
    @DisplayName("Deve retornar lista de autores com sucesso.")
    void retornarTodosAutoresCadastrados() {
        Autor paulo = AutorFixture.entity("Paulo",LocalDate.of(1978, 9, 12),Sexo.MASCULINO,"12345678912");
        Autor maria = AutorFixture.entity("Maria",LocalDate.of(1978, 9, 12),Sexo.FEMININO,"12345678923");
        AutorResponseDto pauloResponse = AutorFixture.responseDto(paulo);
        AutorResponseDto mariaResponse = AutorFixture.responseDto(maria);
        List<Autor> autores = List.of(paulo,maria);
        Pageable pageable = PageRequest.of(0,10);
        Page<Autor> autorPage = new PageImpl<>(autores,pageable, autores.size());

        when(repository.findAll(any(Pageable.class))).thenReturn(autorPage);

        Page<AutorResponseDto> resposta = service.retornarTodosAutoresCadastrados(pageable);

        assertTrue(resposta.getContent().size()== autores.size());
        assertTrue(resposta.getContent().contains(pauloResponse));
        assertTrue(resposta.getContent().contains(mariaResponse));
    }

    @Test
    @DisplayName("Deve retornar um autor buscado poir id.")
    void buscarUmAutorPorId() {
        Autor autor = AutorFixture.entity("Jose",LocalDate.of(1978, 9, 12),Sexo.MASCULINO,"12345678934");
        Long id = autor.getId();

        when(repository.findById(id)).thenReturn(Optional.of(autor));

        AutorResponseDto resposta = service.buscarUmAutorPorId(id);

        assertEquals(id,resposta.id());
        assertEquals(autor.getNome(),resposta.nome());
        assertEquals(autor.getSexo(),resposta.sexo());
        assertEquals(autor.getCpf(),resposta.cpf());
        assertEquals(autor.getDataDeNascimento(),resposta.dataDeNascimento());
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar autor buscado por id.")
    void lancarExcecaoAoBuscarAutorPeloId(){
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        AutorNaoCadastradoException exception = assertThrows(AutorNaoCadastradoException.class,
                ()-> {service.buscarUmAutorPorId(id) ;}
        );

        assertTrue(exception.getMessage().contains(
                "O id: "+id+" não corresponde a nenhum autor cadastrado no nosso banco de dados."
        ));
    }

    @Test
    @DisplayName("Deve retornar um autor buscado por nome.")
    void buscarAutorPeloNome() {
        Autor autor = AutorFixture.entity("Jose",LocalDate.of(1978, 9, 12),Sexo.MASCULINO,"12345678934");
        String nome = autor.getNome();

        when(repository.findByNomeIgnoreCase(nome)).thenReturn(Optional.of(autor));

        AutorResponseDto resposta = service.buscarAutorPeloNome(nome);

        assertEquals(autor.getId(),resposta.id());
        assertEquals(autor.getNome(),resposta.nome());
        assertEquals(autor.getSexo(),resposta.sexo());
        assertEquals(autor.getCpf(),resposta.cpf());
        assertEquals(autor.getDataDeNascimento(),resposta.dataDeNascimento());
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar autor buscado por nome.")
    void lancarExcecaoAoBuscarAutorPeloNome(){
        String nome = "maria";

        when(repository.findByNomeIgnoreCase(nome)).thenReturn(Optional.empty());

        AutorNaoCadastradoException exception = assertThrows(AutorNaoCadastradoException.class,
                ()-> {service.buscarAutorPeloNome(nome);}
        );

        assertTrue(exception.getMessage().contains(
                "O nome: "+nome.toUpperCase()+" não corresponde a nenhum autor" +
                        " cadastrado no nosso banco de dados"
        ));

    }

    @Test
    @DisplayName("Deve atualizar um autor existente e retornar o DTO atualizado")
    void atualizarUmAutor() {
        Autor autor = AutorFixture.entity("Paulo Henrique", LocalDate.of(1978, 9, 12),Sexo.MASCULINO,"12345678934");
        Long id = autor.getId();
        AtualizacaoAutorDto atualizacoes = AutorFixture.atualizacaoDto("Maria",LocalDate.of(2000,12,7),Sexo.FEMININO);
        Autor autorAtualizado = AutorFixture.update(autor,atualizacoes);

        when(repository.findById(id)).thenReturn(Optional.of(autor));
        when(repository.save(any(Autor.class))).thenReturn(autorAtualizado);

        AutorResponseDto resposta = service.atualizarUmAutor(id,atualizacoes);

        assertEquals(id,resposta.id());
        assertEquals(atualizacoes.nome(),resposta.nome());
        assertEquals(atualizacoes.dataDeNascimento(), resposta.dataDeNascimento());
        assertEquals(atualizacoes.sexo(), resposta.sexo());
    }

    @Test
    @DisplayName("Deve apagar um autor existente.")
    void apagar() {
        Autor autor = AutorFixture.entity("Paulo Henrique", LocalDate.of(1978, 9, 12),Sexo.MASCULINO,"12345678934");
        Long id = autor.getId();

        when(repository.findById(id)).thenReturn(Optional.of(autor));

        service.apagar(id);

        verify(repository).delete(autor);
    }

    @Test
    void buscar() {
    }

    @Test
    void testBuscar() {
    }

    @Test
    void temLivroAssociados() {
    }

    @Test
    void buscarPorCpf() {
    }
}