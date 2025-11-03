package com.db.projeto.gerenciamento_de_biblioteca.service.implementacoes;

import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.NovoLivroDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.CategoriaDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.AutorNaoCadastradoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.AutorNaoInformadoException;
import com.db.projeto.gerenciamento_de_biblioteca.mappers.LivroMapper;
import com.db.projeto.gerenciamento_de_biblioteca.model.Autor;
import com.db.projeto.gerenciamento_de_biblioteca.model.Livro;
import com.db.projeto.gerenciamento_de_biblioteca.repository.LivroRepository;
import com.db.projeto.gerenciamento_de_biblioteca.service.LivroServiceI;
import java.util.ArrayList;
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
        List<Autor> autores = validaAutores(dto.idAutores());
        Livro livro = mapper.toEntity(dto,autores);
        livro =repository.save(livro);
        System.out.println(livro.getAutores().size());
        
        return null;
    }

    @Override
    public LivroResponseDto atualizar(LivroAtualizacoesDto dto) {
        return null;
    }

    @Override
    public void apagar(Long id) {

    }

    @Override
    public Page<LivroResponseDto> listarTodos(Pageable pageable) {
        return null;
    }

    @Override
    public LivroResponseDto buscarPorId(Long id) {
        return null;
    }

    @Override
    public LivroResponseDto buscarPorTitulo(String titulo) {
        return null;
    }

    @Override
    public Page<LivroResponseDto> buscarPorCategoria(CategoriaDoLivro categoriaDoLivro) {
        return null;
    }

    @Override
    public Page<LivroResponseDto> buscarPorAutor(CategoriaDoLivro categoriaDoLivro) {
        return null;
    }

    protected List<Autor> validaAutores(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return List.of();
        }

        return ids.stream()
                .map(id -> autorService.buscar(id)
                        .orElseThrow(() -> new AutorNaoCadastradoException(id)))
                .collect(Collectors.toList());
    }

}