package com.db.projeto.gerenciamento_de_biblioteca.repository;

import com.db.projeto.gerenciamento_de_biblioteca.model.Locatario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocatarioRepository extends JpaRepository<Locatario,Long> {
    Optional<Locatario> findByCpf(String cpf);

    Optional<Locatario> findByEmail(String email);
}