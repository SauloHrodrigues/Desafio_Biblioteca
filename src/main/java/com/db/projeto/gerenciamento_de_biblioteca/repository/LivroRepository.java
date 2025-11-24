package com.db.projeto.gerenciamento_de_biblioteca.repository;

import com.db.projeto.gerenciamento_de_biblioteca.enuns.CategoriaDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.StatusDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.model.Livro;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro,Long> {

    Page<Livro> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
    Page<Livro> findByCategoriaDoLivro(CategoriaDoLivro categoriaDoLivro, Pageable pageable);
    Page<Livro> findByStatus(StatusDoLivro status, Pageable pageable);
    Page<Livro> findByAutores_Id(Long idAutor, Pageable pageable);
}