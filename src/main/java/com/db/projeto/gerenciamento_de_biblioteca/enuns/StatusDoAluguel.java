package com.db.projeto.gerenciamento_de_biblioteca.enuns;

public enum StatusDoAluguel {
    ALUGADO("alugado"),
    DEVOLVIDO("devolvido");

    private String status;

    StatusDoAluguel(String status) {
        this.status = status;
    }
}