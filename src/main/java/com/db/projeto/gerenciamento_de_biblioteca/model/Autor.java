package com.db.projeto.gerenciamento_de_biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "autores")
public class Autor extends Pessoa {
    @ManyToMany(mappedBy = "autores")
    private Set<Livro> livros= new HashSet<>();
}