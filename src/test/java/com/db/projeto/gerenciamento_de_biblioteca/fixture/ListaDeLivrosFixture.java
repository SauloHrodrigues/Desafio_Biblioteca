package com.db.projeto.gerenciamento_de_biblioteca.fixture;

import com.db.projeto.gerenciamento_de_biblioteca.enuns.CategoriaDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.GeneroDaPessoa;
import com.db.projeto.gerenciamento_de_biblioteca.model.Autor;
import com.db.projeto.gerenciamento_de_biblioteca.model.Livro;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ListaDeLivrosFixture {
    public static Autor autorPauloCoelho = AutorFixture.entity("paulo coelho", LocalDate.of(2017,9,12),
    GeneroDaPessoa.MASCULINO,"76117960034");
    public static Autor autorGustavoCerbasi = AutorFixture.entity("Gustavo Cerbasi", LocalDate.of(2011,9,12),
    GeneroDaPessoa.MASCULINO,"76117960034");

    public static Livro livro01 = LivroFixture.entity("Casais Inteligentes Enriquecem Juntos",
            LocalDate.of(2004, 1, 1),
            "9788573124898",
            CategoriaDoLivro.ECONOMIA,
            autorGustavoCerbasi);

    public static Livro livro02 = LivroFixture.entity("Adeus, Aposentadoria",
            LocalDate.of(2015, 8, 10),
            "9788543102739",
            CategoriaDoLivro.ECONOMIA,
            autorGustavoCerbasi);

    public static Livro livro03 = LivroFixture.entity("Mais Tempo, Mais Dinheiro",
            LocalDate.of(2016, 5, 9),
            "9788543104986",
            CategoriaDoLivro.AUTOBIOGRAFIA,
            autorGustavoCerbasi);

    public static Livro livro04 = LivroFixture.entity("Investimentos Inteligentes",
            LocalDate.of(2010, 3, 15),
            "9788573127523",
            CategoriaDoLivro.ARTE,
            autorGustavoCerbasi);

    public static Livro livro05 = LivroFixture.entity("Dinheiro: Os Segredos de Quem Tem",
            LocalDate.of(2009, 9, 12),
            "9788573126977",
            CategoriaDoLivro.ARTE,
            autorGustavoCerbasi);

    public static Livro livro06 = LivroFixture.entity("Como Organizar Sua Vida Financeira",
            LocalDate.of(2014, 2, 20),
            "9788543101244",
            CategoriaDoLivro.ARTE,
            autorGustavoCerbasi);

    public static Livro livro07 = LivroFixture.entity("Pais Inteligentes Enriquecem Seus Filhos",
            LocalDate.of(2013, 9, 5),
            "9788573129077",
            CategoriaDoLivro.COMEDIA,
            autorGustavoCerbasi);

    public static Livro livro08 = LivroFixture.entity("O Futuro Enriquecem Juntos",
            LocalDate.of(2018, 10, 1),
            "9788543107420",
            CategoriaDoLivro.COMEDIA,
            autorGustavoCerbasi);

    public static Livro livro09 = LivroFixture.entity("Empreendedores Inteligentes Enriquecem Mais",
            LocalDate.of(2019, 3, 12),
            "9788543109332",
            CategoriaDoLivro.AVENTURA,
            autorGustavoCerbasi);

    public static Livro livro10 = LivroFixture.entity("Enriquecer é Uma Questão de Escolha",
            LocalDate.of(2006, 6, 15),
            "9788573125079",
            CategoriaDoLivro.AVENTURA,
            autorGustavoCerbasi);

    public static Livro livro11 = LivroFixture.entity("O Alquimista",
            LocalDate.of(1988, 4, 15),
            "9788576653721",
            CategoriaDoLivro.FICCAO_CIENTIFICA,
            autorPauloCoelho);

    public static Livro livro12 = LivroFixture.entity("Brida",
            LocalDate.of(1990, 3, 12),
            "9788576653714",
            CategoriaDoLivro.FICCAO_CIENTIFICA,
            autorPauloCoelho);

    public static Livro livro13 = LivroFixture.entity("Veronika Decide Morrer",
            LocalDate.of(1998, 9, 25),
            "9788576653738",
            CategoriaDoLivro.CONTOS,
            autorPauloCoelho);

    public static Livro livro14 = LivroFixture.entity("O Diário de um Mago",
            LocalDate.of(1987, 5, 1),
            "9788576653707",
            CategoriaDoLivro.CONTOS,
            autorPauloCoelho);

    public static Livro livro15 = LivroFixture.entity("As Valkírias",
            LocalDate.of(1992, 6, 10),
            "9788576653745",
            CategoriaDoLivro.AUTOAJUDA,
            autorPauloCoelho);

    public static final List<Livro> livros() {
        List<Livro> lista = new ArrayList<>();
        lista.addAll(List.of(
                livro01,
                livro02,
                livro03,
                livro04,
                livro05,
                livro06,
                livro07,
                livro08,
                livro09,
                livro10,
                livro11,
                livro12,
                livro13,
                livro14,
                livro15
        ));
        return lista;
    }

}
