package com.db.projeto.gerenciamento_de_biblioteca.repository;

import com.db.projeto.gerenciamento_de_biblioteca.model.Aluguel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel,Long> {
    Page<Aluguel> findByDevolvidoTrue(Pageable pageable);
    Page<Aluguel> findByDevolvidoFalse(Pageable pageable);
}
