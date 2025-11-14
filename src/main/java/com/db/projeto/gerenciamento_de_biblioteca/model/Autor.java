package com.db.projeto.gerenciamento_de_biblioteca.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "autores", uniqueConstraints = {
    @UniqueConstraint(name = "uk_autor_cpf", columnNames = "cpf")})
public class Autor extends Pessoa {

    @ManyToMany(mappedBy = "autores" , cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Livro> livros= new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Autor autor)) return false;
        return Objects.equals(getId(), autor.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}