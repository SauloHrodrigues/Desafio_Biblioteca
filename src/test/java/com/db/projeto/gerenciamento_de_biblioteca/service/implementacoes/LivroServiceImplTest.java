package com.db.projeto.gerenciamento_de_biblioteca.service.implementacoes;

import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.NovoLivroDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.CategoriaDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.StatusDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.AutorNaoCadastradoException;
import com.db.projeto.gerenciamento_de_biblioteca.fixture.AutorFixture;
import com.db.projeto.gerenciamento_de_biblioteca.fixture.LivroFixture;
import com.db.projeto.gerenciamento_de_biblioteca.model.Autor;
import com.db.projeto.gerenciamento_de_biblioteca.model.Livro;
import com.db.projeto.gerenciamento_de_biblioteca.repository.LivroRepository;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class LivroServiceImplTest {

    @InjectMocks
    private LivroServiceImpl service;

    @Mock
    private LivroRepository repository;

    @Mock
    private AutorServiceImpl autorService;

    @Test
    @DisplayName("Deve cadastrar um novo livro com sucesso.")
    void cadastrar() {
        NovoLivroDto dto= LivroFixture.requestLivo01();
        Autor autor = AutorFixture.entityAutor01();
        Livro livro = LivroFixture.livro01();
        List<Long> ids = List.of(autor.getId());
        Set<Autor> autores = Set.of(autor);

        for (Long id : ids) {
            when(autorService.buscar(id)).thenReturn(Optional.of(autor));
        }
        when(repository.save(any(Livro.class))).thenReturn(livro);

        LivroResponseDto resposta = service.cadastrar(dto);

        assertNotNull(resposta.id());
        assertEquals(dto.titulo(),resposta.titulo());
        assertEquals(dto.publicacao(),resposta.publicacao());
        assertEquals(dto.isbn(),resposta.isbn());
        assertEquals(dto.categoriaDoLivro(),resposta.categoriaDoLivro());
        assertEquals(dto.idAutores(),resposta.idAutores());
    }

    @Test
    @DisplayName("deve lancarExcecao ao tentar cadastrar livro com autores nao cadastrados.")
    void deveLancarExcecaoAoTentarCadastrarLivroComAutoresNaoCadastrados() {
        NovoLivroDto dto= LivroFixture.requestLivo01();
        Autor autor = AutorFixture.entityAutor01();
        Long idAutor = autor.getId();

        when(autorService.buscar(idAutor)).thenReturn(Optional.empty());

        AutorNaoCadastradoException exception = assertThrows(AutorNaoCadastradoException.class,()->{
            service.cadastrar(dto);
        });

        assertEquals("Não foi localizado nenhum autor para o ID: #{"+idAutor+
                "}",exception.getMessage());
    }

    @Test
    @DisplayName("deve atualizar livro  cadastrados.") // parei aqui...
    void atualizar() {
        Livro livro = LivroFixture.livro01();
        Long id = livro.getId();
        LivroAtualizacoesDto atualizacoesDto = new LivroAtualizacoesDto("Chapelzinho vermelho",null,
                null,CategoriaDoLivro.CONTOS);
        Livro livroAtualizado = LivroFixture.update(livro,atualizacoesDto);

        when(repository.findById(id)).thenReturn(Optional.of(livro));
        when(repository.save(livroAtualizado)).thenReturn(livroAtualizado);

        LivroResponseDto resposta = service.atualizar(livro.getId(), atualizacoesDto);

        assertNotNull(resposta.id());
        assertEquals(atualizacoesDto.titulo(),resposta.titulo());
        assertEquals(livro.getPublicacao(),resposta.publicacao());
        assertEquals(livro.getPublicacao(),resposta.publicacao());
        assertEquals(livro.getIsbn(),resposta.isbn());
        assertEquals(atualizacoesDto.categoriaDoLivro(),resposta.categoriaDoLivro());
    }

    @Test
    @DisplayName("deve apagar livro  cadastrados.")
    void apagar() {
        Livro livro = LivroFixture.livro01();
        Long id = livro.getId();

        when(repository.findById(id)).thenReturn(Optional.of(livro));

        service.apagar(id);

        verify(repository).delete(livro);
    }

    @Test
    @DisplayName("deve retornar todos os livros cadastrados.")
    void listarTodos() {
        Livro livro01 = LivroFixture.livro01();
        Livro livro02 = LivroFixture.livro02();
        List<Livro>livros = List.of(livro01,livro02);
        Pageable pageable = PageRequest.of(0,15);
        Page<Livro> pageLivros = new PageImpl<>(livros,pageable,livros.size());

        when(repository.findAll(any(Pageable.class))).thenReturn(pageLivros);

        Page<LivroResponseDto> resposta = service.listarTodos(pageable);

        assertEquals(livros.size(),resposta.getContent().size());
        assertEquals(livro01.getTitulo(), resposta.getContent().get(0).titulo());
        assertEquals(livro02.getTitulo(), resposta.getContent().get(1).titulo());
        assertEquals(livro01.getIsbn(), resposta.getContent().get(0).isbn());
        assertEquals(livro02.getIsbn(), resposta.getContent().get(1).isbn());

        verify(repository).findAll(pageable);
    }


    @Test
    @DisplayName("deve retornar um livro procurado por id")
    void buscarPorId() {
        Livro livro = LivroFixture.livro01();
        Long id = livro.getId();

        when(repository.findById(id)).thenReturn(Optional.of(livro));

        LivroResponseDto resposta = service.buscarPorId(id);

        assertNotNull(resposta.id());
        assertEquals(livro.getTitulo(), resposta.titulo());
        assertEquals(livro.getIsbn(), resposta.isbn());
        assertEquals(livro.getCategoriaDoLivro(), resposta.categoriaDoLivro());
        assertEquals(livro.getPublicacao(), resposta.publicacao());
    }

    @Test
    @DisplayName("deve retornar um livro procurado por titulo")
    void buscarPorTitulo() {
        Livro livro01 = LivroFixture.livro01();
        String titulo = "Inteligentes";
        Pageable pageable = PageRequest.of(0, 10);
        List<Livro> livros = List.of(livro01);
        Page<Livro> pageLivros = new PageImpl<>(livros, pageable, livros.size());

        when(repository.findByTituloContainingIgnoreCase(titulo, pageable))
                .thenReturn(pageLivros);

        Page<LivroResponseDto> resposta = service.buscarPorTitulo(titulo, pageable);

        assertNotNull(resposta);
        assertTrue(resposta.getContent().get(0).titulo().contains(titulo));

    }

    @Test
    @DisplayName("deve retornar um livro procurado por Categoria")
    void buscarPorCategoria() {
        Livro livro= LivroFixture.livro01();
        CategoriaDoLivro categoria = livro.getCategoriaDoLivro();

        Pageable pageable = PageRequest.of(0, 10);
        List<Livro> livros = List.of(livro);
        Page<Livro> pageLivros = new PageImpl<>(livros, pageable, livros.size());

        when(repository.findByCategoriaDoLivro(categoria, pageable))
                .thenReturn(pageLivros);

        Page<LivroResponseDto> resposta = service.buscarPorCategoria(categoria, pageable);

        assertNotNull(resposta);
        assertFalse(resposta.isEmpty());
        assertEquals(livros.size(), resposta.getTotalElements());
        assertEquals(livros.get(0).getCategoriaDoLivro(), resposta.getContent().get(0).categoriaDoLivro());

    }

    @Test
    @DisplayName("deve retornar um livro procurado por Autor")
    void buscarPorAutor() {
        Livro livro = LivroFixture.livro01();
        long id = livro.getId();
        Autor autor = livro.getAutores().iterator().next();
        Pageable pageable = PageRequest.of(0, 10);
        List<Livro> livros = List.of(livro);

        Page<Livro> pageLivros = new PageImpl<>(livros, pageable, livros.size());

        when(autorService.buscar(id)).thenReturn(Optional.of(autor));
        when(repository.findByAutores_Id(id, pageable)).thenReturn(pageLivros);

        Page<LivroResponseDto> resposta = service.buscarPorAutor(id, pageable);

        assertNotNull(resposta);
        assertFalse(resposta.isEmpty());
        assertEquals(livros.size(), resposta.getTotalElements());

    }

    @Test
    @DisplayName("deve retornar um autor cadastrado ")
    void validaAutor() {
        Autor autor = AutorFixture.entityAutor01();
        Long id = autor.getId();

        when(autorService.buscar(id)).thenReturn(Optional.of(autor));

        Autor resposta = service.validaAutor(id);

        assertEquals(id,resposta.getId());
        assertEquals(autor.getNome(), resposta.getNome());
        assertEquals(autor.getCpf(), resposta.getCpf());
        assertEquals(autor.getDataDeNascimento(), resposta.getDataDeNascimento());
        assertEquals(autor.getLivros(), resposta.getLivros());
    }

    @Test
    @DisplayName("deve lançar excecao por não achar um autor cadastrado ")
    void validaAutorDeveLancarExcecao() {
        Long id =999L;
        when(autorService.buscar(id)).thenReturn(Optional.empty());

        AutorNaoCadastradoException exception = assertThrows(AutorNaoCadastradoException.class, ()->{
            service.validaAutor(id);
        });

        assertEquals(exception.getMessage(),"Não foi localizado nenhum autor para o ID: #{"+id+"}");
        verify(autorService).buscar(id);
    }


    @Test
    @DisplayName("deve retornar status Disponivel.")
    void getStatusDoLivroDisponivel() {
        int status = 0;

        StatusDoLivro resposta = service.getStatusDoLivro(status);

        assertEquals(StatusDoLivro.DISPONIVEL,resposta);
    }

    @Test
    @DisplayName("deve retornar status Indisponivel.")
    void getStatusDoLivroIndisponivel() {
        int status = 1;

        StatusDoLivro resposta = service.getStatusDoLivro(status);

        assertEquals(StatusDoLivro.INDISPONIVEL,resposta);
    }

    @Test
    @DisplayName("deve retornar um livro salvo.")
    void salvar() {
        Livro livro = LivroFixture.livro01();

        when(repository.save(livro)).thenReturn(livro);

        service.salvar(livro);

        verify(repository).save(livro);
    }
}