package com.db.projeto.gerenciamento_de_biblioteca.enuns;

public enum StatusDoLivro {

    DISPONIVEL(0),
    INDISPONIVEL(1);

    private final int codigo;

    StatusDoLivro(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public static StatusDoLivro fromCodigo(int codigo) {
        for (StatusDoLivro s : values()) {
            if (s.getCodigo() == codigo) {
                return s;
            }
        }
        throw new IllegalArgumentException("Código inválido: " + codigo);
    }
}