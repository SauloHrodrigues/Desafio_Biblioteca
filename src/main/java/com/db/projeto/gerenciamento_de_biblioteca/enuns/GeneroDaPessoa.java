package com.db.projeto.gerenciamento_de_biblioteca.enuns;

public enum GeneroDaPessoa {
    MASCULINO("masculino"),
    FEMININO("feminino");

    private String sexo;

    GeneroDaPessoa(String sexo) {
        this.sexo = sexo;
    }
}