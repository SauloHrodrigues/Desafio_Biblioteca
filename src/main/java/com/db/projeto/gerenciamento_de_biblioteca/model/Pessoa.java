package com.db.projeto.gerenciamento_de_biblioteca.model;

import com.db.projeto.gerenciamento_de_biblioteca.enuns.Sexo;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDate dataDeNacimento;
    private String CPF;
    private Sexo  sexo;
}