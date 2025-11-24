package com.db.projeto.gerenciamento_de_biblioteca.service.implementacoes;

import com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel.AluguelResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel.NovoAluguelDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.Sexo;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.StatusDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.exception.aluguel.AluguelNaoEncontradoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.livro.LivroIndisponivelException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.livro.LivroNaoEncontradoException;
import com.db.projeto.gerenciamento_de_biblioteca.fixture.AluguelFixiture;
import com.db.projeto.gerenciamento_de_biblioteca.fixture.LivroFixture;
import com.db.projeto.gerenciamento_de_biblioteca.fixture.LocatarioFixture;
import com.db.projeto.gerenciamento_de_biblioteca.model.Aluguel;
import com.db.projeto.gerenciamento_de_biblioteca.model.Livro;
import com.db.projeto.gerenciamento_de_biblioteca.model.Locatario;
import com.db.projeto.gerenciamento_de_biblioteca.repository.AluguelRepository;
import java.time.LocalDate;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AluguelServiceImplTest {

    @InjectMocks
    private AluguelServiceImpl service;
    @Mock
    private AluguelRepository repository;
    @Mock
    private LocatarioServiceImpl locatarioService;
    @Mock
    private LivroServiceImpl livroService;


    @Test // *********************************************** REVER
    @DisplayName("Deve realizar o cadastro de um novo aluguel com sucesso.")
    void deveCadastrarUmAluguelComSucesso() {
        NovoAluguelDto dto = AluguelFixiture.requestDto();
        List<Livro> livros = AluguelFixiture.livros;
        Locatario locatario = LocatarioFixture.entity();
        Long idLocatario = locatario.getId();
        Aluguel aluguelSemId = AluguelFixiture.entitySemId();
        Aluguel aluguel = AluguelFixiture.entity();

        when(livroService.buscarListaDeLivros(dto.idsDosLivros())).thenReturn(livros);
        when(locatarioService.buscar(idLocatario)).thenReturn(locatario);
        when(repository.save(aluguelSemId)).thenReturn(aluguel);

        AluguelResponseDto resposta = service.cadastrar(dto);

        assertNotNull(resposta.id());
        assertEquals(dto.retirada(), resposta.retirada());
        assertEquals(dto.devolucao(), resposta.devolucao());
        assertEquals(dto.idDoLocatario(), resposta.locatario().getId());
    }

//    @Test
//    @DisplayName("Deve lançar exceção de livro não cadastrado, ao cadastrar um  aluguel.")
//    void deveLancarExcecaoDeLivroNaoCadastradoAoCadastrarUmAluguel(){
//        NovoAluguelDto dto = AluguelFixiture.requestDto();
//        Livro livro01 = LivroFixture.livro01();
//        Livro livro02 = LivroFixture.livro02();
//
//        AluguelFixiture.idsDosLivros = List.of(livro01.getId(), livro02.getId(),9L);
//        List<Livro> livros = List.of(livro01, livro02);
//
//        when(livroService.buscarListaDeLivros(dto.idsDosLivros())).thenReturn(livros);
//
//        LivroNaoEncontradoException resposta = assertThrows(LivroNaoEncontradoException.class,()->{
//            service.cadastrar(dto);
//        });
//
//        assertTrue(resposta.getMessage().contains("Não foi localizado nenhum livro para o ID:"));
//
//    }


@Test
@DisplayName("Deve lançar exceção de livro indisponivel ao cadastrar um  aluguel.")
void deveLancarExcecaoDeLivroIndisponivelAoCadastrarUmAluguel() {

    Livro livro01 = LivroFixture.livro01();
    Livro livro02 = LivroFixture.livro02();
    livro02.setStatus(StatusDoLivro.INDISPONIVEL);
    AluguelFixiture.livros = List.of(livro01, livro02);
    AluguelFixiture.idsDosLivros = List.of(livro01.getId(), livro02.getId());
    NovoAluguelDto dto = AluguelFixiture.requestDto();
    List<Livro> livros = AluguelFixiture.livros;

    when(livroService.buscarListaDeLivros(dto.idsDosLivros())).thenReturn(livros);

    LivroIndisponivelException resposta = assertThrows(LivroIndisponivelException.class, () -> {
        service.cadastrar(dto);
    });

    assertEquals("O livro para o ID: #{" + livro02.getId() + "}, está indisponível.",
            resposta.getMessage());
}

    @Test
    @DisplayName("Deve listar todos os alugueis cadastrados.")
    void listarTodosOsAlugueisCadastrados() {
        List<Aluguel> aluguelList= List.of(AluguelFixiture.entity(),AluguelFixiture.entity(),AluguelFixiture.entity());
        Pageable pageable = PageRequest.of(0,10);
        Page<Aluguel> pageAlugueis = new PageImpl<>(aluguelList,pageable,aluguelList.size());

        when(repository.findAll(pageable)).thenReturn(pageAlugueis);

        Page<AluguelResponseDto> resposta = service.listarTodos(pageable);

        assertEquals(aluguelList.size(),resposta.getContent().size());
        verify(repository).findAll(pageable);
    }

    @Test
    @DisplayName("Deve retornar alugueis buscado por id.")
    void deveBuscarAluguelPorId() {
        Aluguel aluguel = AluguelFixiture.entity();
        Long id = aluguel.getId();

        when(repository.findById(id)).thenReturn(Optional.of(aluguel));

        AluguelResponseDto resposta = service.buscarPorId(id);

        assertNotNull(resposta.id());
        assertEquals(aluguel.getLivros().size(),resposta.livros().size());
        assertEquals(aluguel.getLocatario(),resposta.locatario());
        assertEquals(aluguel.getRetirada(),resposta.retirada());
        assertEquals(aluguel.getDevolucao(),resposta.devolucao());

        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Deve lançar exceção de aluguel não encontrado ao buscar por id.")
    void deveLanacarExcecaoDaAluguelNaoEncontradoAoBuscarPorId() {
        Long id = 99L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        AluguelNaoEncontradoException excecao = assertThrows(AluguelNaoEncontradoException.class,()->{
            service.buscarPorId(id);
        });

        assertEquals("Não foi localizado nenhum aluguel com o ID: #{"+id+"}",
                excecao.getMessage());

        verify(repository).findById(id);
    }

    @Test
    @DisplayName("Deve retornar todos os alugueis filtrados por pendentes.")
    void deveRetornarTodosOsAlugueisFiltradosPorPendente() {
        Aluguel aluguel01= AluguelFixiture.entity();
        Aluguel aluguel02= AluguelFixiture.entity();
        Aluguel aluguel03= AluguelFixiture.entity();

        List<Aluguel> aluguelList= List.of(aluguel01,aluguel02,aluguel03);
        Pageable pageable = PageRequest.of(0,10);
        Page<Aluguel> pageAlugueis = new PageImpl<>(aluguelList,pageable,aluguelList.size());

        when(repository.findByDevolvidoFalse(pageable)).thenReturn(pageAlugueis);

        Page<AluguelResponseDto> resposta = service.listarAlugueis("pendentes",pageable);

        assertFalse(resposta.isEmpty());
        assertEquals(3,resposta.getContent().size());

        verify(repository).findByDevolvidoFalse(pageable);
    }

    @Test
    @DisplayName("Deve retornar todos os alugueis filtrados por devolvidos.")
    void deveRetornarTodosOsAlugueisFiltradosPorDevolvidos() {
        Aluguel aluguel01= AluguelFixiture.entity();
        Aluguel aluguel02= AluguelFixiture.entity();
        Aluguel aluguel03= AluguelFixiture.entity();
        aluguel03.setDevolvido(true);

        List<Aluguel> aluguelList= List.of(aluguel01,aluguel02,aluguel03);
        Pageable pageable = PageRequest.of(0,10);
        Page<Aluguel> pageAlugueis = new PageImpl<>(aluguelList,pageable,aluguelList.size());

        when(repository.findByDevolvidoTrue(pageable)).thenReturn(pageAlugueis);

        Page<AluguelResponseDto> resposta = service.listarAlugueis("devolvidos",pageable);

        assertFalse(resposta.isEmpty());
        assertEquals(3,resposta.getContent().size());

        verify(repository).findByDevolvidoTrue(pageable);
    }


    @Test
    @DisplayName("Deve fazer a devolução de um aluguel.")
    void deveDevolverAluguel() {
        Livro livro = LivroFixture.livro01();
        livro.setStatus(StatusDoLivro.INDISPONIVEL);
        List<Livro> livros = List.of(livro);

        Aluguel aluguel = AluguelFixiture.entity();
        Long id = aluguel.getId();
        aluguel.setLivros(livros);

        when(repository.findById(id)).thenReturn(Optional.of(aluguel));
        when(repository.save(aluguel)).thenReturn(aluguel);

        AluguelResponseDto resposta = service.devolverAluguel(id);
        Livro livroDaResposta = resposta.livros().iterator().next();

        assertNotNull(resposta);
        assertEquals(id, resposta.id());
        assertTrue(resposta.devolvido());
        assertTrue(livroDaResposta.getStatus().equals(StatusDoLivro.DISPONIVEL));

    }



    @Test //************************ rever falso positivo
    @DisplayName("Deve vincular os livros ao aluguél.")
    void vincularLivrosAoAluguel() {
        Aluguel aluguel = AluguelFixiture.entity();
        Livro livro01 = LivroFixture.livro01();
        Livro livro02 = LivroFixture.livro02();
        aluguel.setLivros(List.of(livro01,livro02));

        when(livroService.salvar(any(Livro.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        service.vincularLivrosAoAluguel(aluguel);
        assertTrue(aluguel.getLivros().size() == 2);
        assertTrue(aluguel.getLivros().get(0).getStatus().equals(StatusDoLivro.INDISPONIVEL));
        assertTrue(aluguel.getLivros().get(1).getStatus().equals(StatusDoLivro.INDISPONIVEL));
    }



//    @Test
//    @DisplayName("Deve retornar uma lista de livros disponiveis e existentes.")
//    void validaLivros() {
//        Livro livro01 = ListaDeLivrosFixture.livro01;
//        Livro livro02 = ListaDeLivrosFixture.livro02;
//        Livro livro03 = ListaDeLivrosFixture.livro03;
//        List<Livro> livros = List.of(livro01,livro02,livro03);
//        List<Long> ids = List.of(livro01.getId(),livro02.getId(),livro03.getId());
//
//        when(livroService.buscarListaDeLivros(ids)).thenReturn(livros);
//
//        List<Livro> resposta = service.validaLivros(ids);
//
//        assertTrue(resposta.contains(livro01));
//        assertTrue(resposta.contains(livro02));
//        assertTrue(resposta.contains(livro03));
//    }
//
//    @Test
//    @DisplayName("Deve lançar exceção de livro não encontrado.")
//    void deveLancarExcecaoDeLivroNaoEncontradoAoValidaLivros() {
//        Livro livro01 = ListaDeLivrosFixture.livro01;
//        Livro livro02 = ListaDeLivrosFixture.livro02;
//        Livro livro03 = ListaDeLivrosFixture.livro03;
//        List<Livro> livros = List.of(livro01,livro02,livro03);
//        List<Long> ids = List.of(livro01.getId(),livro02.getId(),livro03.getId(),99L);
//
//        when(livroService.buscarListaDeLivros(ids)).thenReturn(livros);
//
//        LivroNaoEncontradoException resposta = assertThrows(LivroNaoEncontradoException.class,()->{
//                service.validaLivros(ids);}
//        );
//
//        assertEquals("Não foi localizado nenhum livro para o ID: #{99}",
//                resposta.getMessage());
//    }
//
//    @Test
//    @DisplayName("Deve lançar exceção de livro indisponivel.")
//    void deveLancarExcecaoDeLivroIndisponivelAoValidaLivros() {
//        Livro livro01 = ListaDeLivrosFixture.livro07;
//        Livro livro02 = ListaDeLivrosFixture.livro08;
//        Livro livro03 = ListaDeLivrosFixture.livro09;
//        livro03.setStatus(StatusDoLivro.INDISPONIVEL);
//        List<Livro> livros = List.of(livro01,livro02,livro03);
//        List<Long> ids = List.of(livro01.getId(),livro02.getId(),livro03.getId());
//
//        when(livroService.buscarListaDeLivros(ids)).thenReturn(livros);
//
//        LivroIndisponivelException resposta = assertThrows(LivroIndisponivelException.class,()->{
//                service.validaLivros(ids);}
//        );
//
//        assertEquals("O livro para o ID: #{"+livro03.getId()+"}, está indisponível.",
//                resposta.getMessage());
//    }
//
//    @Test
//    void salvarLivro() {
//        Livro livro = ListaDeLivrosFixture.livro09;
//
//        when(livroService.salvar(livro)).thenReturn(livro);
//
//        Livro resposta = service.salvarLivro(livro);
//
//        assertEquals(livro,resposta);
//        verify(livroService).salvar(livro);
//    }
}