package com.db.projeto.gerenciamento_de_biblioteca.repository;

import com.db.projeto.gerenciamento_de_biblioteca.model.Autor;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("""
                SELECT COUNT(l) > 0
                FROM Livro l
                JOIN l.autores a
                WHERE a.id = :autorId
            """)
    boolean existsLivrosByAutorId(@Param("autorId") Long autorId);

    Optional<Autor> findByNomeIgnoreCase(String nome);

    List<Autor> findByNomeContainingIgnoreCase(String nome);

    Optional<Autor> findByCpf(String cpf);
}