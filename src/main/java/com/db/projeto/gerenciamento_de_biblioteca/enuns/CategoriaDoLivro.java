package com.db.projeto.gerenciamento_de_biblioteca.enuns;

public enum CategoriaDoLivro {
    ROMANCE("Romance"),
    FICCAO_CIENTIFICA("Ficção Científica"),
    FANTASIA("Fantasia"),
    TERROR("Terror"),
    SUSPENSE("Suspense"),
    MISTERIO("Mistério"),
    AVENTURA("Aventura"),
    DRAMA("Drama"),
    COMEDIA("Comédia"),
    POESIA("Poesia"),
    BIOGRAFIA("Biografia"),
    AUTOBIOGRAFIA("Autobiografia"),
    HISTORIA("História"),
    RELIGIAO("Religião"),
    FILOSOFIA("Filosofia"),
    AUTOAJUDA("Autoajuda"),
    EDUCACAO("Educação"),
    INFANTOJUVENIL("Infantojuvenil"),
    HQ("História em Quadrinhos"),
    MANGÁ("Mangá"),
    TECNOLOGIA("Tecnologia"),
    CIENCIA("Ciência"),
    DIREITO("Direito"),
    ECONOMIA("Economia"),
    POLITICA("Política"),
    ARTE("Arte"),
    MUSICA("Música"),
    CULINARIA("Culinária"),
    SAUDE("Saúde"),
    ESPORTE("Esporte"),
    VIAGEM("Viagem"),
    ENSAIO("Ensaio"),
    CRONICA("Crônica"),
    CONTOS("Contos");

    private final String categoria;

    CategoriaDoLivro(String categoria) {
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }
}