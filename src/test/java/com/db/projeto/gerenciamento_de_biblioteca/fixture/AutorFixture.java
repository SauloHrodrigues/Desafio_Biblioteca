package com.db.projeto.gerenciamento_de_biblioteca.fixture;

import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AtualizacaoAutorDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AutorResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.NovoAutorDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.sexo;
import com.db.projeto.gerenciamento_de_biblioteca.model.Autor;
import java.time.LocalDate;
import java.util.HashSet;

public class AutorFixture {
    static Long id=0L;

    public static NovoAutorDto requestDto(String nome, LocalDate dataDeNascimento, sexo sexo, String cpf){
        return new NovoAutorDto(
                nome,
                dataDeNascimento,
                cpf,
                sexo);
    }

    public static Autor entity(NovoAutorDto dto){
        return entity(dto.nome(),dto.dataDeNascimento(),dto.sexo(), dto.cpf());
    }

    public static Autor entity(String nome, LocalDate dataDeNascimento, sexo sexo, String cpf){
        id++;
        return Autor.builder()
                .id(id)
                .nome(nome)
                .dataDeNascimento(dataDeNascimento)
                .cpf(cpf)
                .sexo(sexo)
                .livros(new HashSet<>())
                .build();
    }

    public static Autor update(Autor autor, AtualizacaoAutorDto dto){

        return Autor.builder()
                .id(autor.getId())
                .nome(dto.nome())
                .dataDeNascimento(dto.dataDeNascimento())
                .cpf(autor.getCpf())
                .sexo(dto.sexo())
                .livros(autor.getLivros())
                .build();
    }

    public static AutorResponseDto responseDto(Autor autor){
        return  new AutorResponseDto(
                autor.getId(),
                autor.getNome(),
                autor.getDataDeNascimento(),
                autor.getCpf(),
                autor.getSexo(),
                autor.getLivros()
        );
    }

    public static AtualizacaoAutorDto atualizacaoDto(String nome, LocalDate dataDeNascimento, sexo sexo){
        return new AtualizacaoAutorDto(nome,dataDeNascimento, sexo);
    }
}
