package com.db.projeto.gerenciamento_de_biblioteca.service.implementacoes;

import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.LocatarioAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.LocatarioResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.NovoLocatarioDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.Sexo;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.CpfJaCadastradoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.locatario.EmailJaCadastradoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.locatario.LocatarioNaoEncontradoException;
import com.db.projeto.gerenciamento_de_biblioteca.fixture.LocatarioFixture;
import com.db.projeto.gerenciamento_de_biblioteca.mappers.LocatarioMapper;
import com.db.projeto.gerenciamento_de_biblioteca.model.Locatario;
import com.db.projeto.gerenciamento_de_biblioteca.repository.LocatarioRepository;
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
class LocatarioServiceImplTest {

    @InjectMocks
    private LocatarioServiceImpl service;

    @Mock
    private LocatarioRepository repository;


    @BeforeEach
    void setUp(){
        LocatarioFixture.id= 1L;
        LocatarioFixture.nome = "José Martins";
        LocatarioFixture.dataDeNascimento = LocalDate.parse("2000-09-12");
        LocatarioFixture.cpf = "09067001082";
        LocatarioFixture.sexo = Sexo.MASCULINO;
        LocatarioFixture.telefone = "11991694599";
        LocatarioFixture.email = "teste@gmail.com";
    }

    @Test
    @DisplayName("Deve realizar o cadastro de um novo locatário com sucesso.")
    void deveCadastrarUmLocatarioComSucesso() {
        Long id = LocatarioFixture.id;
        NovoLocatarioDto dto = LocatarioFixture.request();
        Locatario locatario = LocatarioFixture.entity();
        Locatario locatarioSalvo = LocatarioFixture.entity();

        when(repository.findByCpf(LocatarioFixture.cpf)).thenReturn(Optional.empty());
        when(repository.findByEmail(LocatarioFixture.email)).thenReturn(Optional.empty());
        when(repository.save(any(Locatario.class))).thenReturn(locatario);

        LocatarioResponseDto resposta = service.cadastrar(dto);

        assertNotNull(resposta.id());
        assertEquals(dto.nome(),resposta.nome());
        assertEquals(dto.dataDeNascimento(),resposta.dataDeNascimento());
        assertEquals(dto.cpf(),resposta.cpf());
        assertEquals(dto.sexo(),resposta.sexo());
        assertEquals(dto.telefone(),resposta.telefone());
        assertEquals(dto.email(),resposta.email());
    }

    @Test
    @DisplayName("Deve lançar excessão ao tentar cadastrar um cpf ja cadastrado.")
    void deveLancarExcessaoAoCadastrarCpfJaCadastrado() {
        NovoLocatarioDto dto = LocatarioFixture.request();
        Locatario locatario = LocatarioFixture.entity();
        String cpf = locatario.getCpf();
        String mensagemEsperada = "Já existe um 'locatario' registrado para o CPF '"+cpf+"'";

        when(repository.findByCpf(cpf)).thenReturn(Optional.of(locatario));

        CpfJaCadastradoException ex = assertThrows(CpfJaCadastradoException.class,
                ()->{service.cadastrar(dto);});

        assertEquals(mensagemEsperada,ex.getMessage());
    }

    @Test
    @DisplayName("Deve lançar excessão ao tentar cadastrar um email ja cadastrado.")
    void deveLancarExcessaoAoCadastrarEmailJaCadastrado() {
        NovoLocatarioDto dto = LocatarioFixture.request();
        Locatario locatario = LocatarioFixture.entity();
        String email = locatario.getEmail();
        String mensagemEsperada = "Já existe um locatário registrado para o email '{"+email+"}'";

        when(repository.findByEmail(email)).thenReturn(Optional.of(locatario));

        EmailJaCadastradoException ex = assertThrows(EmailJaCadastradoException.class,
                ()->{service.cadastrar(dto);});

        assertEquals(mensagemEsperada,ex.getMessage());
    }

    @Test
    @DisplayName("Deve retornar todos os locatarios cadastrados.")
    void deveListarTodosLocatariosCadastrados() {
        List<Locatario> locatarios= List.of(LocatarioFixture.entity(), LocatarioFixture.entity());
        Pageable pageable = PageRequest.of(0,10);
        Page<Locatario> pageLocatario = new PageImpl<>(locatarios,pageable,locatarios.size());

        when(repository.findAll(pageable)).thenReturn(pageLocatario);

        Page<LocatarioResponseDto> resposta = service.listarTodos(pageable);

        assertEquals(locatarios.size(),resposta.getContent().size());
        verify(repository).findAll(pageable);
    }

    @Test
    @DisplayName("Deve retornar locatario cadastrados, buscado por id.")
    void buscarLocatarioBuscadoPorId() {
        Locatario locatario = LocatarioFixture.entity();
        Long id = locatario.getId();

        when(repository.findById(id)).thenReturn(Optional.of(locatario));

        LocatarioResponseDto resposta = service.buscarPorId(id);

        assertNotNull(resposta.id());
        assertEquals(locatario.getNome(),resposta.nome());
        assertEquals(locatario.getDataDeNascimento(),resposta.dataDeNascimento());
        assertEquals(locatario.getCpf(),resposta.cpf());
        assertEquals(locatario.getSexo(),resposta.sexo());
        assertEquals(locatario.getTelefone(),resposta.telefone());
        assertEquals(locatario.getEmail(),resposta.email());
    }

    @Test
    @DisplayName("Deve retornar locatario atualizado.")
    void deveAtualizarUmLocatario() {
        Locatario locatario = LocatarioFixture.entity();
        Long id = locatario.getId();
        LocatarioAtualizacoesDto atualizacoesDto = new LocatarioAtualizacoesDto("novo nome",
                null,Sexo.FEMININO,null,"test02@gmail.com");
        Locatario locatarioAtualizado = locatario;
        locatarioAtualizado.setNome(atualizacoesDto.nome());
        locatarioAtualizado.setSexo(Sexo.FEMININO);
        locatarioAtualizado.setEmail(atualizacoesDto.email());

        when(repository.findById(id)).thenReturn(Optional.of(locatario));
        when(repository.save(locatario)).thenReturn(locatarioAtualizado);

        LocatarioResponseDto resposta = service.atualizar(id,atualizacoesDto);

        assertEquals(locatario.getId(),resposta.id());
        assertEquals(atualizacoesDto.nome(),resposta.nome());
        assertEquals(locatario.getDataDeNascimento(),resposta.dataDeNascimento());
        assertEquals(atualizacoesDto.sexo(),resposta.sexo());
        assertEquals(locatario.getTelefone(),resposta.telefone());
        assertEquals(locatario.getEmail(),resposta.email());
    }

    @Test
    void apagar() {
        //        todo
    }

    @Test
    @DisplayName("Deve validar cpf para cadastrado.")
    void validarCpf() {
        Locatario locatario = LocatarioFixture.entity();
        String cpf = locatario.getCpf();

        when(repository.findByCpf(cpf)).thenReturn(Optional.empty());

        service.validarCpf(cpf);

        verify(repository).findByCpf(cpf);
    }

    @Test
    @DisplayName("Deve lançar Excessão de cpf já cadastrado.")
    void lancaExcessaoDeCpfJáCadastrado() {
        Locatario locatario = LocatarioFixture.entity();
        String cpf = locatario.getCpf();

        when(repository.findByCpf(cpf)).thenReturn(Optional.of(locatario));

        CpfJaCadastradoException exception = assertThrows(CpfJaCadastradoException.class, ()->{
            service.validarCpf(cpf);
        });

        assertEquals("Já existe um 'locatario' registrado para o CPF '"+cpf+"'", exception.getMessage());

        verify(repository).findByCpf(cpf);
    }

    @Test
    @DisplayName("Deve validar email para cadastrado.")
    void validarEmail() {
        String email = "email@gmail.com";

        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        repository.findByEmail(email);

        verify(repository).findByEmail(email);
    }

    @Test
    @DisplayName("Deve lancar excessão de email já cadastrado.")
    void deveLancarExcessaoDeEmailJaCadastrado() {
        Locatario locatario = LocatarioFixture.entity();
        String email = locatario.getEmail();

        when(repository.findByEmail(email)).thenReturn(Optional.of(locatario));

        EmailJaCadastradoException exception = assertThrows(EmailJaCadastradoException.class, ()->{
            service.validarEmail(email);
        });

        assertEquals("Já existe um locatário registrado para o email '{"+email+"}'",
                exception.getMessage());

        verify(repository).findByEmail(email);
    }

    @Test
    @DisplayName("Deve retonar um locatario cadastrado.")
    void deveBuscarLocatarioPorId() {
        Locatario locatario = LocatarioFixture.entity();
        Long id = locatario.getId();

        when(repository.findById(id)).thenReturn(Optional.of(locatario));

        Locatario resposta = service.buscar(id);

        verify(repository).findById(id);

        assertEquals(locatario.getId(),resposta.getId());
        assertEquals(locatario.getNome(),resposta.getNome());
        assertEquals(locatario.getDataDeNascimento(),resposta.getDataDeNascimento());
        assertEquals(locatario.getCpf(),resposta.getCpf());
        assertEquals(locatario.getSexo(),resposta.getSexo());
        assertEquals(locatario.getTelefone(),resposta.getTelefone());
        assertEquals(locatario.getEmail(),resposta.getEmail());
    }

    @Test
    @DisplayName("Deve lançar excessão de locatario não cadastrado.")
    void deveLancarExcessaoDeLocatarioNaoCadastrado() {
        Long id = 999L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        LocatarioNaoEncontradoException exception = assertThrows(LocatarioNaoEncontradoException.class, ()->{
            service.buscar(id);
        });

        verify(repository).findById(id);

        assertEquals("Não foi possível localizar nenhum locatário para o ID: #{"+id+"}",
                exception.getMessage());
    }

    @Test
    @DisplayName("Deve salvar um locatario.")
    void salvar() {
        Locatario locatario = LocatarioFixture.entity();

        when(repository.save(locatario)).thenReturn(locatario);

        service.salvar(locatario);

        verify(repository).save(locatario);
    }
}