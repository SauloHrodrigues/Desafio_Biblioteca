package com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel;

import com.db.projeto.gerenciamento_de_biblioteca.model.Livro;
import com.db.projeto.gerenciamento_de_biblioteca.model.Locatario;
import java.time.LocalDate;
import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NovoAluguel {
   private LocalDate retirada;
    private LocalDate devolucao;
    private boolean devolvido;
    private Set<Livro> livros;
    private Locatario locatario;
}