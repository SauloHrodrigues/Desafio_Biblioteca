package com.db.projeto.gerenciamento_de_biblioteca.service.implementacoes;

import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AtualizacaoAutorDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AutorResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.NovoAutorDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.Sexo;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.AutorComLivroNoBancoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.AutorJaCadastradoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.AutorNaoCadastradoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.CpfJaCadastradoException;
import com.db.projeto.gerenciamento_de_biblioteca.fixture.AutorFixture;
import com.db.projeto.gerenciamento_de_biblioteca.fixture.LivroFixture;
import com.db.projeto.gerenciamento_de_biblioteca.model.Autor;
import com.db.projeto.gerenciamento_de_biblioteca.model.Livro;
import com.db.projeto.gerenciamento_de_biblioteca.repository.AutorRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AutorServiceImplTest {

    @InjectMocks
    private AutorServiceImpl service;

    @Mock
    private AutorRepository repository;


    @Test
    @DisplayName("Deve lançar excecao ao tentar cadastrar um novo autor.")
    void deveLancarExcecaoDeAutorJaCadastrado() {
        NovoAutorDto dto = AutorFixture.requestDto01();
        Autor autor = AutorFixture.entityAutor01();

        when(repository.findByNomeIgnoreCase(dto.nome())).thenReturn(Optional.of(autor));

        AutorJaCadastradoException exception = assertThrows(AutorJaCadastradoException.class,
                () -> {
                    service.cadastrar(dto);
                });

        assertTrue(exception.getMessage().contains(
                "Autor já cadastrado para o nome '{" + autor.getNome() + "'}"
        ));
    }

    @Test
    @DisplayName("Deve lançar excecao ao tentar cadastrar um novo autor, com cpf já cadastrado.")
    void deveLancarExcecaoPorCpfJaCadastrado() {
        NovoAutorDto dto = AutorFixture.requestDto01();
        Autor autor = AutorFixture.entityAutor01();

        when(repository.findByCpf(dto.cpf())).thenReturn(Optional.of(autor));

        CpfJaCadastradoException exception = assertThrows(CpfJaCadastradoException.class,
                () -> {
                    service.cadastrar(dto);
                });

        assertEquals("Já existe um 'autor' registrado para o CPF '" + autor.getCpf() + "'",
                exception.getMessage());
    }


    @Test
    @DisplayName("Deve retornar lista de autores com sucesso.")
    void retornarTodosAutoresCadastrados() {
        Autor autor01 = AutorFixture.entityAutor01();
        Autor autor02 = AutorFixture.entityAutor02();
        List<Autor> autores = List.of(autor01, autor02);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Autor> autorPage = new PageImpl<>(autores, pageable, autores.size());

        when(repository.findAll(pageable)).thenReturn(autorPage);

        Page<AutorResponseDto> resposta = service.retornarTodosAutoresCadastrados(pageable);

        assertTrue(resposta.getContent().size() == autores.size());
        assertTrue(resposta.getContent().contains(AutorFixture.response(autor01)));
        assertTrue(resposta.getContent().contains(AutorFixture.response(autor02)));
    }

    @Test
    @DisplayName("Deve retornar um autor buscado por id.")
    void buscarUmAutorPorId() {
        Autor autor = AutorFixture.entityAutor01();
        Long id = autor.getId();

        when(repository.findById(id)).thenReturn(Optional.of(autor));

        AutorResponseDto resposta = service.buscarUmAutorPorId(id);

        assertEquals(id, resposta.id());
        assertEquals(autor.getNome(), resposta.nome());
        assertEquals(autor.getSexo(), resposta.sexo());
        assertEquals(autor.getCpf(), resposta.cpf());
        assertEquals(autor.getDataDeNascimento(), resposta.dataDeNascimento());
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar autor buscado por id.")
    void lancarExcecaoAoBuscarAutorPeloId() {
        Long id = 99L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        AutorNaoCadastradoException exception = assertThrows(AutorNaoCadastradoException.class,
                () -> {
                    service.buscarUmAutorPorId(id);
                }
        );

        assertTrue(exception.getMessage().contains(
                "Não foi localizado nenhum autor para o ID: #{" + id + "}"
        ));
    }

    @Test
    @DisplayName("Deve retornar um autor buscado por nome.")
    void buscarAutorPeloNome() {
        Autor autor = AutorFixture.entityAutor01();
        String nome = autor.getNome();

        when(repository.findByNomeContainingIgnoreCase(nome)).thenReturn(List.of(autor));

        List<AutorResponseDto> resposta = service.buscarAutorPeloNome(nome);

        assertTrue(resposta.size() == 1);
        assertEquals(autor.getId(), resposta.get(0).id());
        assertEquals(autor.getNome(), resposta.get(0).nome());
        assertEquals(autor.getSexo(), resposta.get(0).sexo());
        assertEquals(autor.getCpf(), resposta.get(0).cpf());
        assertEquals(autor.getDataDeNascimento(), resposta.get(0).dataDeNascimento());
    }

    @Test
    @DisplayName("Deve lançar exceção quando não encontrar autor buscado por nome.")
    void lancarExcecaoAoBuscarAutorPeloNome() {
        String nome = "maria";

        when(repository.findByNomeContainingIgnoreCase(nome)).thenReturn(List.of());

        AutorNaoCadastradoException exception = assertThrows(AutorNaoCadastradoException.class,
                () -> {
                    service.buscarAutorPeloNome(nome);
                }
        );

        assertTrue(exception.getMessage().contains(
                "Não foi encontrado nenhum autor com o nome '{" + nome + "}'"
        ));
    }

    @Test
    @DisplayName("Deve atualizar um autor existente e retornar o DTO atualizado")
    void atualizarUmAutor() {
        Autor autor = AutorFixture.entityAutor01();
        Long id = autor.getId();
        AtualizacaoAutorDto atualizacoes = new AtualizacaoAutorDto("zeze", LocalDate.of(2000, 12, 7), Sexo.FEMININO);
        Autor autorAtualizado = AutorFixture.update(autor, atualizacoes);

        when(repository.findById(id)).thenReturn(Optional.of(autor));
        when(repository.save(autorAtualizado)).thenReturn(autorAtualizado);

        AutorResponseDto resposta = service.atualizarUmAutor(id, atualizacoes);

        assertEquals(id, resposta.id());
        assertEquals(atualizacoes.nome(), resposta.nome());
        assertEquals(atualizacoes.dataDeNascimento(), resposta.dataDeNascimento());
        assertEquals(atualizacoes.sexo(), resposta.sexo());
    }

    @Test
    @DisplayName("Deve apagar um autor existente.")
    void apagar() {
        Autor autor = AutorFixture.entityAutor01();
        Long id = autor.getId();

        when(repository.findById(id)).thenReturn(Optional.of(autor));

        service.apagar(id);

        verify(repository).delete(autor);
    }

    @Test
    @DisplayName("Deve lançar excecao ao tentar apagar um autor vinculado a livro existente.")
    void lancarExcecaoAoApagar() {
        Autor autor = AutorFixture.entityAutor01();
        Long id = autor.getId();
        Livro livro = LivroFixture.livro01();

        autor.setLivros(Set.of(livro));

        when(repository.findById(id)).thenReturn(Optional.of(autor));
        when(repository.existsLivrosByAutorId(autor.getId())).thenReturn(true);

        AutorComLivroNoBancoException exception= assertThrows(AutorComLivroNoBancoException.class,
                ()-> service.apagar(id));

      assertTrue(exception.getMessage().contains(
           "Não foi possível remover o autor de ID \'{ "+id+" }'" +
                   " pois os livros dos seguintes ID’sestão associados a ele: "+
                   livro.getId()
      ));

    }
}