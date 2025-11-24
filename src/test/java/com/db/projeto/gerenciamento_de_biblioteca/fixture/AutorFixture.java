package com.db.projeto.gerenciamento_de_biblioteca.fixture;

import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AtualizacaoAutorDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AutorResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.NovoAutorDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.Sexo;
import com.db.projeto.gerenciamento_de_biblioteca.model.Autor;
import java.time.LocalDate;

public class AutorFixture {

    final static Autor AUTOR01() {
        return Autor.builder()
                .id(1L)
                .nome("Jose maria")
                .dataDeNascimento(LocalDate.of(2000, 02, 10))
                .cpf("51752495098")
                .sexo(Sexo.MASCULINO)
                .build();
    }

    final static Autor AUTOR02() {
        return Autor.builder()
                .id(2L)
                .nome("Cecília Meireles")
                .dataDeNascimento(LocalDate.of(1901, 11, 7))
                .cpf("12345678900")
                .sexo(Sexo.FEMININO)
                .build();
    }

    public static NovoAutorDto requestDto02() {
        return new NovoAutorDto(
                AUTOR02().getNome(), AUTOR02().getDataDeNascimento(), AUTOR02().getCpf(),
                AUTOR02().getSexo());
    }

    public static NovoAutorDto requestDto01() {
           return new NovoAutorDto(
                    AUTOR01().getNome(), AUTOR01().getDataDeNascimento(), AUTOR01().getCpf(),
                    AUTOR01().getSexo());
    }

    public static Autor entityAutor01() {
        return AUTOR01();
    }

    public static Autor entityAutor02() {
            return AUTOR02();
    }

    public static Autor update(Autor autor, AtualizacaoAutorDto dto) {
        String nome = dto.nome() == null ? autor.getNome() : dto.nome();
        LocalDate dataDeNascimento = dto.dataDeNascimento() == null ? autor.getDataDeNascimento() : dto.dataDeNascimento();
        Sexo sexo = dto.sexo() == null ? autor.getSexo() : dto.sexo();

        return Autor.builder()
                .id(autor.getId())
                .cpf(autor.getCpf())
                .livros(autor.getLivros())
                .nome(nome)
                .dataDeNascimento(dataDeNascimento)
                .sexo(sexo)
                .build();
    }

    public static AutorResponseDto response(Autor autor) {
        return new AutorResponseDto(
                autor.getId(), autor.getNome(), autor.getDataDeNascimento(),
                autor.getCpf(), autor.getSexo(), autor.getLivros());
    }
}