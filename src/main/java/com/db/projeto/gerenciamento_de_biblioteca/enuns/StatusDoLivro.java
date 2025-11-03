package com.db.projeto.gerenciamento_de_biblioteca.enuns;

public enum StatusDoLivro {
    ALUGADO("alugado"),
    DISPONIVEL("disponivel");

    private String status;

    StatusDoLivro(String status) {
        this.status = status;
    }
}