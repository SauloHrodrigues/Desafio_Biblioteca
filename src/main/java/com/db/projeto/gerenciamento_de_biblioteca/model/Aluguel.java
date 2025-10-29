package com.db.projeto.gerenciamento_de_biblioteca.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "alugueis")
public class Aluguel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate retirada;
    private LocalDate devolucao;

    @OneToMany(mappedBy = "aluguel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Livro> livros;

    @ManyToOne(optional = false)
    @JoinColumn(name = "locatario_id", nullable = false)
    private Locatario locatario;
}