package com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel;

import java.time.LocalDate;
import java.util.List;

public record NovoAluguelDto(
        LocalDate retirada,
        LocalDate devolucao,
        boolean devolvido,
        Long idDoLocatario,
        List<Long> idsDosLivros
) {
}
