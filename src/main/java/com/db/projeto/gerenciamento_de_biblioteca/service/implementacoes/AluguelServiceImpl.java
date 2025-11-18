package com.db.projeto.gerenciamento_de_biblioteca.service.implementacoes;

import com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel.AluguelResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel.NovoAluguel;
import com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel.NovoAluguelDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.StatusDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.exception.aluguel.AluguelDevolvidoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.aluguel.AluguelNaoEncontradoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.livro.LivroIndisponivelException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.livro.LivroNaoEncontradoException;
import com.db.projeto.gerenciamento_de_biblioteca.mappers.AluguelMapper;
import com.db.projeto.gerenciamento_de_biblioteca.model.Aluguel;
import com.db.projeto.gerenciamento_de_biblioteca.model.Livro;
import com.db.projeto.gerenciamento_de_biblioteca.model.Locatario;
import com.db.projeto.gerenciamento_de_biblioteca.repository.AluguelRepository;
import com.db.projeto.gerenciamento_de_biblioteca.service.AluguelServiceI;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AluguelServiceImpl implements AluguelServiceI {

    private final AluguelRepository repository;
    private final LocatarioServiceImpl locatarioService;
    private final LivroServiceImpl livroService;
    private AluguelMapper mapper = AluguelMapper.INSTANCE;

    @Override
    public AluguelResponseDto cadastrar(NovoAluguelDto dto) {
        List<Livro> livros = validaLivros(dto.idsDosLivros());
        Locatario locatario = locatarioService.buscar(dto.idDoLocatario());
        Aluguel aluguel = mapper.toEntity(dto);

        aluguel.setLocatario(locatario);
        aluguel.setLivros(livros);
        aluguel = repository.save(aluguel);
        vincularLivrosAoAluguel(aluguel);

        return mapper.toResponse(aluguel);
    }

    @Override
    public Page<AluguelResponseDto> listarTodos(Pageable pageable) {
        Page<AluguelResponseDto> resposta = repository.findAll(pageable).map(mapper::toResponse);
        return resposta;
    }

    @Override
    public AluguelResponseDto buscarPorId(Long id) {
        Aluguel aluguel = buscar(id);
        return mapper.toResponse(aluguel);
    }

    @Override
    public Page<AluguelResponseDto> listarAlugueis(String status, Pageable pageable) {
        Page<AluguelResponseDto> resposta = null;

        if (status.equalsIgnoreCase("pendentes")) {
            resposta = repository.findByDevolvidoFalse(pageable).map(mapper::toResponse);
        }

        if (status.equalsIgnoreCase("devolvidos")) {
            resposta = repository.findByDevolvidoTrue(pageable).map(mapper::toResponse);
        }

        if (resposta.isEmpty()) {
            throw new AluguelNaoEncontradoException("Não há alugueis " + status + ".");
        }
        return resposta;
    }

    @Transactional
    @Override
    public AluguelResponseDto devolverAluguel(Long id) {

        Aluguel aluguel = buscar(id);

        if (aluguel.isDevolvido()) {
            throw new AluguelDevolvidoException(id);
        }

        for (Livro livro : aluguel.getLivros()) {
            livro.setStatus(StatusDoLivro.DISPONIVEL);
        }

        aluguel.setDevolvido(true);
        aluguel = repository.save(aluguel);
        return mapper.toResponse(aluguel);
    }


    protected Aluguel buscar(Long id) {
        return repository.findById(id).orElseThrow(() -> new AluguelNaoEncontradoException(id));
    }

    protected void vincularLivrosAoAluguel(Aluguel aluguel) {
        for (Livro livro : aluguel.getLivros()) {
            livro.setStatus(StatusDoLivro.INDISPONIVEL);
            livro.setAluguel(aluguel);
            salvarLivro(livro);
        }
    }

    protected List<Livro> validaLivros(List<Long> ids) {
        List<Livro> livros = livroService.buscarListaDeLivros(ids);

        for (Long id : ids) {
            boolean existe = livros.stream()
                    .anyMatch(livro -> livro.getId().equals(id));

            if (!existe) {
                throw new LivroNaoEncontradoException(id);
            }
        }

        for (Livro livro : livros) {
            if (livro.getStatus().equals(StatusDoLivro.INDISPONIVEL)) {
                throw new LivroIndisponivelException(livro.getId());
            }
        }

        return livros;
    }

    protected Livro salvarLivro(Livro livro) {
        return livroService.salvar(livro);
    }

}