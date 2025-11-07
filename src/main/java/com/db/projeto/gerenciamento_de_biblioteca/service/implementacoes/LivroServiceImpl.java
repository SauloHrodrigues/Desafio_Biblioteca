package com.db.projeto.gerenciamento_de_biblioteca.service.implementacoes;

import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.NovoLivroDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.CategoriaDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.StatusDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.AutorNaoCadastradoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.livro.LivroNaoEncontradoException;
import com.db.projeto.gerenciamento_de_biblioteca.mappers.LivroMapper;
import com.db.projeto.gerenciamento_de_biblioteca.model.Autor;
import com.db.projeto.gerenciamento_de_biblioteca.model.Livro;
import com.db.projeto.gerenciamento_de_biblioteca.repository.LivroRepository;
import com.db.projeto.gerenciamento_de_biblioteca.service.LivroServiceI;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LivroServiceImpl implements LivroServiceI {

    private final LivroRepository repository;
    private final AutorServiceImpl autorService;
    private LivroMapper mapper = LivroMapper.INSTANCE;

    @Override
    public LivroResponseDto cadastrar(NovoLivroDto dto) {
        List<Autor> autores = buscar(dto.idAutores());
        Livro livro = mapper.toEntity(dto, autores);
        livro = repository.save(livro);
        return mapper.toResponse(livro);
    }

    @Override
    public LivroResponseDto atualizar(Long id, LivroAtualizacoesDto dto) {
        Livro livro = buscar(id);
        livro = mapper.update(livro, dto);
        livro = salvar(livro);
        return mapper.toResponse(livro);
    }

    @Override
    public void apagar(Long id) {
        //TODO VALIDAR LIVRO NÃO ALUGADO
        Livro livro = buscar(id);
        repository.delete(livro);
    }

    @Override
    public Page<LivroResponseDto> listarTodos(int status, Pageable pageable) {
        StatusDoLivro aux = getStatusDoLivro(status);
        Page<LivroResponseDto> todosOsLivro = repository.findByStatus(aux, pageable).map(mapper::toResponse);
        return todosOsLivro;
    }

    @Override
    public Page<LivroResponseDto> listarTodos(Pageable pageable) {
        Page<LivroResponseDto> todosOsLivro = repository.findAll(pageable).map(mapper::toResponse);
        return todosOsLivro;
    }

    @Override
    public LivroResponseDto buscarPorId(Long id) {
        Livro livro = repository.findById(id).orElseThrow(() -> new LivroNaoEncontradoException(id));
        return mapper.toResponse(livro);
    }

    @Override
    public Page<LivroResponseDto> buscarPorTitulo(String titulo, Pageable pageable) {
        Page<LivroResponseDto> todosOsLivro = repository.findByTituloContainingIgnoreCase(titulo, pageable).map(mapper::toResponse);
        if (todosOsLivro.isEmpty()) {
            throw new LivroNaoEncontradoException("titulo", titulo);
        }
        return todosOsLivro;
    }

    @Override
    public Page<LivroResponseDto> buscarPorCategoria(CategoriaDoLivro categoria, Pageable pageable) {
        Page<LivroResponseDto> livrosDaCategorias = repository.findByCategoriaDoLivro(categoria, pageable).map(mapper::toResponse);
        if (livrosDaCategorias.isEmpty()) {
            throw new LivroNaoEncontradoException("categoria", categoria.getCategoria());
        }
        return livrosDaCategorias;
    }

    @Override
    public Page<LivroResponseDto> buscarPorAutor(Long idAutor, Pageable pageable) {
        Autor autor = validaAutor(idAutor);
        Page<LivroResponseDto> livrosDoAutor = repository.findByAutores_Id(idAutor, pageable).map(mapper::toResponse);
        if (livrosDoAutor.isEmpty()) {
            throw new LivroNaoEncontradoException("autor", autor.getNome());
        }
        return livrosDoAutor;
    }

    protected Autor validaAutor(Long id) {
        return autorService.buscar(id).orElseThrow(() -> new AutorNaoCadastradoException(id));
    }

    protected StatusDoLivro getStatusDoLivro(int status) {
        if (status == 0) {
            return StatusDoLivro.DISPONIVEL;
        } else if (status == 1) {
            return StatusDoLivro.INDISPONIVEL;
        } else {
            throw new RuntimeException("Status inválido");
        }
    }

    protected Livro salvar(Livro livro) {
        return repository.save(livro);
    }

    protected Livro buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AutorNaoCadastradoException(id));
    }

    protected List<Autor> buscar(List<Long> ids) {
        return ids.stream()
                .map(id -> autorService.buscar(id)
                        .orElseThrow(() -> new AutorNaoCadastradoException(id)))
                .collect(Collectors.toList());
    }
}