package com.db.projeto.gerenciamento_de_biblioteca.service.implementacoes;

import com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel.AluguelResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel.NovoAluguel;
import com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel.NovoAluguelDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.StatusDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.exception.aluguel.AluguelNaoEncontradoException;
import com.db.projeto.gerenciamento_de_biblioteca.exception.livro.LivroIndisponivelException;
import com.db.projeto.gerenciamento_de_biblioteca.mappers.AluguelMapper;
import com.db.projeto.gerenciamento_de_biblioteca.model.Aluguel;
import com.db.projeto.gerenciamento_de_biblioteca.model.Livro;
import com.db.projeto.gerenciamento_de_biblioteca.model.Locatario;
import com.db.projeto.gerenciamento_de_biblioteca.repository.AluguelRepository;
import com.db.projeto.gerenciamento_de_biblioteca.service.AluguelServiceI;
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
        NovoAluguel novoAluguel= criarAluguel(dto);
        Aluguel aluguel = mapper.toEntity(novoAluguel);
        aluguel = salvarAluguel(aluguel);
        vincularLivrosAoAluguel(aluguel);
        return mapper.toResponse(aluguel);
    }

    @Override
    public Page<AluguelResponseDto> listarTodos(Pageable pageable){
        Page<AluguelResponseDto> resposta = repository.findAll(pageable).map(mapper::toResponse);
        return resposta;
    }

    @Override
    public AluguelResponseDto buscarPorId(Long id){
        Aluguel aluguel = buscar(id);
        return mapper.toResponse(aluguel);
    }


    @Override
    public Page<AluguelResponseDto> listarAlugueis(String status,Pageable pageable){
        Page<AluguelResponseDto> resposta=null;

        if(status.equalsIgnoreCase("pendentes")){
             resposta = repository.findByDevolvidoFalse(pageable).map(mapper::toResponse);
        }

        if(status.equalsIgnoreCase("devolvidos")){
             resposta = repository.findByDevolvidoTrue(pageable).map(mapper::toResponse);
        }

       if(resposta.isEmpty()){
           throw new AluguelNaoEncontradoException("Não há alugueis "+status+".");
       }
        return resposta;
    }


    protected Aluguel buscar(Long id){
        return repository.findById(id).orElseThrow(()-> new AluguelNaoEncontradoException(id));
    }

    @Override
    public AluguelResponseDto devolverAluguel(Long id){
        Aluguel aluguel = buscar(id);
        devolverLivros(aluguel.getLivros());
        aluguel.setDevolvido(true);
        aluguel= salvarAluguel(aluguel);
        return mapper.toResponse(aluguel);
    }

    protected void devolverLivros(List<Livro>livros){
        for(Livro livro: livros){
            livro.setStatus(StatusDoLivro.DISPONIVEL);
            salvarLivro(livro);
        }
    }
    protected NovoAluguel criarAluguel(NovoAluguelDto dto){
        Locatario locatario = getLocatario(dto.idDoLocatario());
        Set<Livro> livros = validaLivros(dto.idsDosLivros());
        LocalDate retirada = getDataDaRetirada(dto.retirada());
        LocalDate devolucao = getDataDaDevolucao(dto.devolucao());

        NovoAluguel aluguel= NovoAluguel.builder()
                .retirada(retirada)
                .devolucao(devolucao)
                .devolvido(false)
                .livros(livros)
                .locatario(locatario)
                .build();
        return aluguel;
    }
    protected void vincularLivrosAoAluguel(Aluguel aluguel){
        for (Livro livro: aluguel.getLivros()) {
            livro.setStatus(StatusDoLivro.INDISPONIVEL);
            livro.setAluguel(aluguel);
            salvarLivro(livro);
        }
    }
    protected Aluguel salvarAluguel(Aluguel aluguel){
        return repository.save(aluguel);
    }
    protected LocalDate getDataDaRetirada(LocalDate retirada){

        if(retirada == null){
            return LocalDate.now();
        }else {
            return retirada;
        }
    }
    protected LocalDate getDataDaDevolucao(LocalDate devolucao){
        if(devolucao == null){
            return LocalDate.now().plusDays(2);
        }else {
            return devolucao;
        }
    }
    protected Locatario getLocatario(Long idLocatario){
        return locatarioService.buscar(idLocatario);
    }
    protected Set<Livro> validaLivros(List<Long> ids){
        Set<Livro> livros = new HashSet<>();

        for(Long id:ids){
            Livro livro = livroService.buscar(id);
            if(livro.getStatus().equals(StatusDoLivro.DISPONIVEL)){
                livros.add(livro);
            } else {
                throw new LivroIndisponivelException(id);
            }
        }
        return livros;
    }
    protected Livro salvarLivro(Livro livro){
        return livroService.salvar(livro);
    }

}