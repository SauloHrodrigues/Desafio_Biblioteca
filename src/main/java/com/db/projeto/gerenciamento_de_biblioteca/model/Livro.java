package com.db.projeto.gerenciamento_de_biblioteca.model;

import com.db.projeto.gerenciamento_de_biblioteca.enuns.CategoriaDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.StatusDoLivro;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @Column(name = "data_publicacao", nullable = false)
    private LocalDate publicacao;
    private String isbn;
    @Enumerated(EnumType.STRING)
    private CategoriaDoLivro categoriaDoLivro;
    @Enumerated(EnumType.ORDINAL)
    private StatusDoLivro status = StatusDoLivro.DISPONIVEL;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "autores_livros",
            joinColumns = @JoinColumn(name = "livro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    @JsonIgnore
    private Set<Autor> autores;

    @ManyToOne
    @JoinColumn(name = "aluguel_id")
    @JsonIgnore
    private Aluguel aluguel;
}