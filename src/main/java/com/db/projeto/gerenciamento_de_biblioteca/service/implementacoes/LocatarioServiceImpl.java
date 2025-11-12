package com.db.projeto.gerenciamento_de_biblioteca.service.implementacoes;

import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.LocatarioAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.LocatarioResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.NovoLocatarioDto;
import com.db.projeto.gerenciamento_de_biblioteca.exception.autor.CpfJaCadastradoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.locatario.EmailJaCadastradoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.locatario.LocatarioNaoEncontradoException;
import com.db.projeto.gerenciamento_de_biblioteca.mappers.LocatarioMapper;
import com.db.projeto.gerenciamento_de_biblioteca.model.Locatario;
import com.db.projeto.gerenciamento_de_biblioteca.repository.LocatarioRepository;
import com.db.projeto.gerenciamento_de_biblioteca.service.LocatarioServiceI;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocatarioServiceImpl implements LocatarioServiceI {
    private final LocatarioRepository repository;
    private LocatarioMapper mapper = LocatarioMapper.INSTANCE;

    @Override
    public LocatarioResponseDto cadastrar(NovoLocatarioDto dto) {
        validarCpf(dto.cpf());
        validarEmail(dto.email());
        Locatario locatario = mapper.toEntity(dto);
        locatario = repository.save(locatario);
        return mapper.toResponseDto(locatario);
    }

    @Override
    public Page<LocatarioResponseDto> listarTodos(Pageable pageable){
        Page<LocatarioResponseDto> resposta = repository.findAll(pageable).map(mapper::toResponseDto);
        return resposta;
    }

    public LocatarioResponseDto buscarPorId(Long id){
        Locatario locatario = buscar(id);
        return mapper.toResponseDto(locatario);
    }

    @Override
    public LocatarioResponseDto atualizar(Long id, LocatarioAtualizacoesDto dto){
        Locatario locatario = buscar(id);
        if(dto.email()!= null && dto.email()!= locatario.getEmail()){
            validarEmail(dto.email());
        }

        locatario = mapper.update(locatario, dto);
        locatario = salvar(locatario);
        return mapper.toResponseDto(locatario);
    }

    @Override
    public void apagar(Long id){
//        TODO
    }

    protected void validarCpf(String cpf){
        Optional<Locatario> locatario = repository.findByCpf(cpf);
        if(locatario.isPresent()){
            throw new CpfJaCadastradoException("locatario",cpf);
        }
    }

    protected void validarEmail(String email){
        Optional<Locatario> locatario = repository.findByEmail(email);
        if(locatario.isPresent()){
            throw new EmailJaCadastradoException(email);
        }
    }

    protected Locatario buscar(Long id){
        return repository.findById(id).orElseThrow(
                ()-> new LocatarioNaoEncontradoException(id)
        );
    }

    protected Locatario salvar(Locatario locatario){
        return repository.save(locatario);
    }

}
