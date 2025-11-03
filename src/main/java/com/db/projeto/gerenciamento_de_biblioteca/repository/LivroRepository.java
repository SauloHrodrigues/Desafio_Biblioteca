package com.db.projeto.gerenciamento_de_biblioteca.repository;

import com.db.projeto.gerenciamento_de_biblioteca.enuns.CategoriaDoLivro;
import com.db.projeto.gerenciamento_de_biblioteca.model.Livro;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LivroRepository extends JpaRepository<Livro,Long> {

    Optional<Livro> findByTituloIgnoreCase(String titulo);
    List<Livro> findByCategoriaDoLivro(CategoriaDoLivro categoriaDoLivro);
    List<Livro> findByAutores_Id(long id);
}