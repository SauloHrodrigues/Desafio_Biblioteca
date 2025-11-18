package com.db.projeto.gerenciamento_de_biblioteca.fixture;

import com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel.NovoAluguelDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.StatusDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.model.Aluguel;
import com.db.projeto.gerenciamento_de_biblioteca.model.Livro;
import com.db.projeto.gerenciamento_de_biblioteca.model.Locatario;
import java.time.LocalDate;
import java.util.List;

public class AluguelFixiture {
    public static Long id;
    public static LocalDate retirada;
    public static LocalDate devolucao;
    public static boolean devolvido;
    public static Long idDoLocatario;
    public static List<Long> idsDosLivros;
    public static List<Livro> livros;

    public static Locatario locatario;

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