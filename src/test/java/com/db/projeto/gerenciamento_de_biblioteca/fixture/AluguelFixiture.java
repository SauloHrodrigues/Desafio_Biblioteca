package com.db.projeto.gerenciamento_de_biblioteca.fixture;

import com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel.NovoAluguelDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.StatusDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.model.Aluguel;
import com.db.projeto.gerenciamento_de_biblioteca.model.Livro;
import com.db.projeto.gerenciamento_de_biblioteca.model.Locatario;
import java.time.LocalDate;
import java.util.List;

public class AluguelFixiture {
    public static Long id= 1L;
    public static LocalDate retirada = LocalDate.of(2025,11,9);
    public static LocalDate devolucao = LocalDate.of(2025,11,15);
    public static boolean devolvido = false;
    public static Long idDoLocatario = LocatarioFixture.entity().getId();
    public static List<Long> idsDosLivros = List.of(LivroFixture.livro01().getId(),LivroFixture.livro02().getId());
    public static List<Livro> livros = List.of(LivroFixture.livro01());
    public static Locatario locatario = LocatarioFixture.entity();

    public static NovoAluguelDto requestDto(){
        return new NovoAluguelDto(retirada,devolucao,devolucao,idDoLocatario,idsDosLivros);
    }

    public static Aluguel entity(){
        return new Aluguel(id,retirada,devolucao, devolvido,livros,locatario);
    }

    public static Aluguel entitySemId(){
        return Aluguel.builder()
                .retirada(retirada)
                .devolucao(devolucao)
                .devolvido(devolvido)
                .livros(livros)
                .locatario(locatario)
                .build();
    }
}