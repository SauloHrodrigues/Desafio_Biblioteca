package com.db.projeto.gerenciamento_de_biblioteca.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "locatarios", uniqueConstraints = {
        @UniqueConstraint(name = "uk_locatario_cpf", columnNames = "cpf")})
public class Locatario extends Pessoa{
    private String telefone;
    private String email;
    @OneToMany(mappedBy = "locatario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Aluguel> alugueis = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Locatario locatario)) return false;
        return Objects.equals(getId(), locatario.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}