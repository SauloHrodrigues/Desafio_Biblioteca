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
import java.util.Optional;
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
        if(repository.findByCpf(dto.cpf()).isPresent()){
            throw new CpfJaCadastradoException(dto.cpf());
        }

        if(buscar(dto.nome()).isPresent()){
            throw new AutorJaCadastradoException("Autor já cadastrado para o nome '{"
                    +dto.nome()+"'}");
        }
        Autor autor = mapper.toEntity(dto);
        autor= repository.save(autor);
        return mapper.toResponse(autor);
    }

    @Override
    public Page<AutorResponseDto> retornarTodosAutoresCadastrados(Pageable pageable) {
        Page<AutorResponseDto> autores = repository.findAll(pageable).map(mapper::toResponse);
        return autores;
    }

    @Override
    public AutorResponseDto buscarUmAutorPorId(Long id) {
        Autor autor = buscar(id).orElseThrow(() -> new AutorNaoCadastradoException(id));
        return mapper.toResponse(autor);
    }

    @Override
    public AutorResponseDto buscarAutorPeloNome(String nome) {
        Autor autor = buscar(nome).orElseThrow(() -> new AutorNaoCadastradoException(nome));
        return mapper.toResponse(autor);
    }

    @Override
    public AutorResponseDto atualizarUmAutor(Long id, AtualizacaoAutorDto atualizacoes) {
        Autor autor = buscar(id).orElseThrow(()-> new AutorNaoCadastradoException(id));
        Autor novoAutor = mapper.update(autor,atualizacoes);
        novoAutor = salvar(novoAutor);
        return mapper.toResponse(novoAutor);
    }

    @Override
    public void apagar(Long id) {
        Autor autor = buscar(id).orElseThrow(()-> new AutorNaoCadastradoException(id));
        repository.delete(autor);
    }

    protected Optional<Autor> buscar(Long id){
        return repository.findById(id);
    }
    protected Optional<Autor> buscar(String nome){
        return repository.findByNomeIgnoreCase(nome);
    }

    protected Autor salvar(Autor autor){
        return repository.save(autor);
    }

    protected void temLivroAssociados(Autor autor){
        if (autor.getLivros()!=null){
            throw new AutorComLivroNoBancoException(autor);
        }
    }
    protected Optional<Autor> buscarPorCpf(String cpf){
        return repository.findByCpf(cpf);
    }

}