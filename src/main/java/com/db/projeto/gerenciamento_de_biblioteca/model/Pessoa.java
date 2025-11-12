package com.db.projeto.gerenciamento_de_biblioteca.model;

import com.db.projeto.gerenciamento_de_biblioteca.enuns.Sexo;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDate;
import java.util.Objects;
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
@MappedSuperclass
public abstract class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDate dataDeNascimento;

    @Column(nullable = false, unique = true)
    private String cpf;
    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa pessoa)) return false;
        return Objects.equals(getId(), pessoa.getId()) && Objects.equals(getNome(), pessoa.getNome()) && Objects.equals(getDataDeNascimento(), pessoa.getDataDeNascimento()) && Objects.equals(getCpf(), pessoa.getCpf()) && getSexo() == pessoa.getSexo();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getDataDeNascimento(), getCpf(), getSexo());
    }
}