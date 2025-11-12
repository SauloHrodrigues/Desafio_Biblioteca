package com.db.projeto.gerenciamento_de_biblioteca.service.implementacoes;

import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.NovoLivroDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.CategoriaDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.Sexo;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.StatusDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.AutorNaoCadastradoException;
import com.db.projeto.gerenciamento_de_biblioteca.fixture.AutorFixture;
import com.db.projeto.gerenciamento_de_biblioteca.fixture.ListaDeLivrosFixture;
import com.db.projeto.gerenciamento_de_biblioteca.fixture.LivroFixture;
import com.db.projeto.gerenciamento_de_biblioteca.model.Autor;
import com.db.projeto.gerenciamento_de_biblioteca.model.Livro;
import com.db.projeto.gerenciamento_de_biblioteca.repository.LivroRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
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

    private Autor autorPauloCoelho;
    private Autor autorGustavoCerbasi;

    private List<Long> listaDeIds;
    private List<Autor> listaDeAutores;

    private Livro livro01;

    private List<Livro> livros;

    @BeforeEach
    void setUp() {
        autorPauloCoelho = AutorFixture.entity("paulo coelho", LocalDate.of(2017,9,12),
                Sexo.MASCULINO,"76117960034");
        autorGustavoCerbasi = AutorFixture.entity("Gustavo Cerbasi", LocalDate.of(2011,9,12),
                Sexo.MASCULINO,"76117960034");

        listaDeIds = new ArrayList<>();
        listaDeAutores= new ArrayList<>();
        livros = ListaDeLivrosFixture.livros();
    }


    @Test
    @DisplayName("Deve cadastrar um novo livro com sucesso.")
    void cadastrar() {
        Long idAutor = autorGustavoCerbasi.getId();
        listaDeIds.add(idAutor);
        listaDeAutores.add(autorGustavoCerbasi);
        NovoLivroDto dto = LivroFixture.request("Dinheiro",LocalDate.of(2011,9,12),"1234567891234", CategoriaDoLivro.AUTOAJUDA, listaDeIds);
        Livro livro = LivroFixture.entity(dto, autorGustavoCerbasi);

        when(autorService.buscar(idAutor)).thenReturn(Optional.of(autorGustavoCerbasi));
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
        Long idAutor = autorGustavoCerbasi.getId();
        listaDeIds.add(idAutor);
        listaDeAutores.add(autorGustavoCerbasi);
        NovoLivroDto dto = LivroFixture.request("Dinheiro",LocalDate.of(2011,9,12),"1234567891234", CategoriaDoLivro.AUTOAJUDA, listaDeIds);
        Livro livro = LivroFixture.entity(dto, autorGustavoCerbasi);

        when(autorService.buscar(idAutor)).thenReturn(Optional.empty());

        AutorNaoCadastradoException exception = assertThrows(AutorNaoCadastradoException.class,()->{
            service.cadastrar(dto);
        });

        assertEquals("Não foi localizado nenhum autor para o ID: #{"+idAutor+
                "}",exception.getMessage());
    }

    @Test
    @DisplayName("deve atualizar livro  cadastrados.")
    void atualizar() {
        LivroAtualizacoesDto atualizacoesDto = new LivroAtualizacoesDto("Chapelzinho vermelho",null,
                null,CategoriaDoLivro.CONTOS);

        when(repository.findById(1L)).thenReturn(Optional.of(livro01));
        when(repository.save(any(Livro.class))).thenReturn(livro01);

        LivroResponseDto resposta = service.atualizar(livro01.getId(), atualizacoesDto);

        assertNotNull(resposta.id());
        assertEquals(atualizacoesDto.titulo(),resposta.titulo());
        assertEquals(livro01.getPublicacao(),resposta.publicacao());
        assertEquals(livro01.getPublicacao(),resposta.publicacao());
        assertEquals(livro01.getIsbn(),resposta.isbn());
        assertEquals(atualizacoesDto.categoriaDoLivro(),resposta.categoriaDoLivro());
    }

    @Test
    @DisplayName("deve apagar livro  cadastrados.")
    void apagar() {
        Long id = livro01.getId();

        when(repository.findById(id)).thenReturn(Optional.of(livro01));

        service.apagar(id);

        verify(repository).delete(livro01);
    }

    @Test
    @DisplayName("deve retornar todos os livros cadastrados.")
    void listarTodos() {
        Pageable pageable = PageRequest.of(0,15);
        Page<Livro> pageLivros = new PageImpl<>(livros,pageable,livros.size());

        when(repository.findAll(any(Pageable.class))).thenReturn(pageLivros);

        Page<LivroResponseDto> resposta = service.listarTodos(pageable);

        assertEquals(15,resposta.getContent().size());
        verify(repository).findAll(pageable);
    }


    @Test
    @DisplayName("deve retornar um livro procurado por id")
    void buscarPorId() {
        Livro livro = ListaDeLivrosFixture.livro01;
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
        String titulo = "dinheiro";
        Pageable pageable = PageRequest.of(0, 10);
        List<Livro> filtrados = livros.stream()
                .filter(l -> l.getTitulo().toLowerCase().contains(titulo))
                .toList();

        Page<Livro> pageFiltrada = new PageImpl<>(filtrados, pageable, filtrados.size());

        when(repository.findByTituloContainingIgnoreCase(titulo, pageable))
                .thenReturn(pageFiltrada);

        Page<LivroResponseDto> resposta = service.buscarPorTitulo(titulo, pageable);

        assertNotNull(resposta);
        assertFalse(resposta.isEmpty());
        assertTrue(resposta.getContent().stream()
                        .allMatch(l -> l.titulo().toLowerCase().contains(titulo)),
                "Todos os livros devem conter '"+titulo+"' no título");
        assertEquals(filtrados.size(), resposta.getTotalElements(),
                "A quantidade de livros deve corresponder ao filtro");
    }

    @Test
    @DisplayName("deve retornar um livro procurado por Categoria")
    void buscarPorCategoria() {
        CategoriaDoLivro categoria = CategoriaDoLivro.AUTOAJUDA;
        Pageable pageable = PageRequest.of(0, 10);
        List<Livro> filtrados = livros.stream()
                .filter(l -> l.getCategoriaDoLivro() == categoria)
                .toList();

        Page<Livro> pageFiltrada = new PageImpl<>(filtrados, pageable, filtrados.size());

        when(repository.findByCategoriaDoLivro(categoria, pageable))
                .thenReturn(pageFiltrada);

        Page<LivroResponseDto> resposta = service.buscarPorCategoria(categoria, pageable);

        assertNotNull(resposta);
        assertFalse(resposta.isEmpty());
        assertTrue(resposta.getContent().stream()
                        .allMatch(l -> l.categoriaDoLivro()==categoria),
                "Todos os livros devem conter '"+categoria+"' no título");
        assertEquals(filtrados.size(), resposta.getTotalElements(),
                "A quantidade de livros deve corresponder ao filtro");
    }

    @Test
    @DisplayName("deve retornar um livro procurado por Autor")
    void buscarPorAutor() {
        Autor autor = ListaDeLivrosFixture.autorGustavoCerbasi;
        long id = autor.getId();
        Pageable pageable = PageRequest.of(0, 10);
        List<Livro> filtrados = livros.stream()
                .filter(l -> l.getAutores().contains(autor))
                .toList();

        Page<Livro> pageFiltrada = new PageImpl<>(filtrados, pageable, filtrados.size());

        when(autorService.buscar(id)).thenReturn(Optional.of(autor));
        when(repository.findByAutores_Id(id, pageable)).thenReturn(pageFiltrada);

        Page<LivroResponseDto> resposta = service.buscarPorAutor(id, pageable);

        assertNotNull(resposta);
        assertFalse(resposta.isEmpty());
        assertTrue(resposta.getContent().stream()
                        .allMatch(l -> l.idAutores().contains(id)),
                "Todos os livros devem conter o id "+id);
        assertEquals(filtrados.size(), resposta.getTotalElements(),
                "A quantidade de livros deve corresponder ao filtro");

    }

    @Test
    @DisplayName("deve retornar um autor cadastrado ")
    void validaAutor() {
        Long id = autorGustavoCerbasi.getId();

        when(autorService.buscar(id)).thenReturn(Optional.of(autorGustavoCerbasi));

        Autor resposta = service.validaAutor(id);

        assertEquals(id,resposta.getId());
        assertEquals(autorGustavoCerbasi.getNome(), resposta.getNome());
        assertEquals(autorGustavoCerbasi.getCpf(), resposta.getCpf());
        assertEquals(autorGustavoCerbasi.getDataDeNascimento(), resposta.getDataDeNascimento());
        assertEquals(autorGustavoCerbasi.getLivros(), resposta.getLivros());
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
        Livro livro = ListaDeLivrosFixture.livro09;

        when(repository.save(livro)).thenReturn(livro);

        service.salvar(livro);

        verify(repository).save(livro);
    }

    @Test
    void buscar() {
    }
}