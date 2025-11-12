package com.db.projeto.gerenciamento_de_biblioteca.fixture;

import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.NovoLocatarioDto;
import com.db.projeto.gerenciamento_de_biblioteca.enuns.Sexo;
import com.db.projeto.gerenciamento_de_biblioteca.model.Aluguel;
import com.db.projeto.gerenciamento_de_biblioteca.model.Locatario;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class LocatarioFixture {
    public static Long id;
    public static String nome;
    public static LocalDate dataDeNascimento;
    public static String cpf;
    public static Sexo sexo;
    public static String telefone;
    public static String email;

    Set<Aluguel> alugueis = new HashSet<>();

    public static NovoLocatarioDto request() {
        return new NovoLocatarioDto(
                nome, dataDeNascimento, cpf, sexo, telefone, email
        );
    }

    public static Locatario entity(){
        Locatario locatario= new Locatario();
        locatario.setId(id);
        locatario.setNome(nome);
        locatario.setDataDeNascimento(dataDeNascimento);
        locatario.setCpf(cpf);
        locatario.setSexo(sexo);
        locatario.setTelefone(telefone);
        locatario.setEmail(email);
        return locatario;
    }

}
