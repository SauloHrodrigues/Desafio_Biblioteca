package com.db.projeto.gerenciamento_de_biblioteca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "autores")
public class Autor extends Pessoa {
    @ManyToMany(mappedBy = "autores")
    private Set<Livro> livros;
}