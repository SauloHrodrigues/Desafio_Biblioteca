package com.db.projeto.gerenciamento_de_biblioteca.service.implementacoes;

import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AtualizacaoAutorDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AutorResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.NovoAutorDto;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.AutorComLivroNoBancoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.AutorJaCadastradoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.AutorNaoCadastradoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.CpfJaCadastradoException;
import com.db.projeto.gerenciamento_de_biblioteca.mappers.AutorMapper;
import com.db.projeto.gerenciamento_de_biblioteca.model.Autor;
import com.db.projeto.gerenciamento_de_biblioteca.repository.AutorRepository;
import com.db.projeto.gerenciamento_de_biblioteca.service.AutorServiceI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AutorServiceImpl implements AutorServiceI {
    private final AutorRepository repository;
    private AutorMapper mapper = AutorMapper.INSTANCE;

    @Override
    public AutorResponseDto cadastrar(NovoAutorDto dto) {
        if (repository.findByCpf(dto.cpf()).isPresent()) {
            throw new CpfJaCadastradoException(dto.cpf());
        }
        validarNomeLivre(dto.nome());
        Autor autor = mapper.toEntity(dto);
        autor = repository.save(autor);
        return mapper.toResponseDto(autor);
    }

    @Override
    public Page<AutorResponseDto> retornarTodosAutoresCadastrados(Pageable pageable) {
        Page<AutorResponseDto> autores = repository.findAll(pageable).map(mapper::toResponseDto);
        return autores;
    }

    @Override
    public AutorResponseDto buscarUmAutorPorId(Long id) {
        Autor autor = buscar(id).orElseThrow(() -> new AutorNaoCadastradoException(id));
        return mapper.toResponseDto(autor);
    }

    @Override
    public List<AutorResponseDto> buscarAutorPeloNome(String nome) {
        List<Autor> autor = repository.findByNomeContainingIgnoreCase(nome);

        if (autor.isEmpty()) {
            throw new AutorNaoCadastradoException(nome);
        }
        return mapper.toResponseDto(autor);
    }

    @Override
    public AutorResponseDto atualizarUmAutor(Long id, AtualizacaoAutorDto atualizacoes) {
        Autor autor = buscar(id).orElseThrow(() -> new AutorNaoCadastradoException(id));
        validarNomeLivre(atualizacoes.nome());
        Autor novoAutor = mapper.update(autor, atualizacoes);
        novoAutor = salvar(novoAutor);
        return mapper.toResponseDto(novoAutor);
    }

    @Override
    public void apagar(Long id) {
        Autor autor = buscar(id).orElseThrow(() -> new AutorNaoCadastradoException(id));
        validarAutorSemLivroAssociados(autor);
        repository.delete(autor);
    }

    protected void validarNomeLivre(String nome) {
        if (repository.findByNomeIgnoreCase(nome).isPresent()) {
            throw new AutorJaCadastradoException("Autor já cadastrado para o nome '{"
                    + nome + "'}");
        }
    }

    protected Optional<Autor> buscar(Long id) {
        return repository.findById(id);
    }

    protected List<Autor> buscar(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }


    protected Autor salvar(Autor autor) {
        return repository.save(autor);
    }

    protected void validarAutorSemLivroAssociados(Autor autor) {
        if (!autor.getLivros().isEmpty()) {
            String ids = autor.getLivros().stream()
                    .map(livro -> String.valueOf(livro.getId()))
                    .collect(Collectors.joining(", "));
            String mensagem = "Não foi possível remover o autor de ID \'{ " + autor.getId() + " }\' pois os livros dos seguintes ID’s" +
                    "estão associados a ele: " + ids;
            throw new AutorComLivroNoBancoException(mensagem);
        }
    }
}