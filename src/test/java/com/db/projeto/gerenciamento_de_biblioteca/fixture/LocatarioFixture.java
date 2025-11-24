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
    public static Long id = 1L;
    public static String nome = "João Victor";
    public static LocalDate dataDeNascimento = LocalDate.of(2000,9,21);
    public static String cpf = "45535805009";
    public static Sexo sexo= Sexo.MASCULINO;
    public static String telefone = "19999335566";
    public static String email = "teste@gmail.com";

    Set<Aluguel> alugueis = new HashSet<>();

    public static NovoLocatarioDto request() {
        return new NovoLocatarioDto(
                nome, dataDeNascimento, cpf, sexo, telefone, email
        );
    }

    public static Locatario entity(){
        String cpfTratado= cpf.replaceAll("\\D", "");

        Locatario locatario= new Locatario();
        locatario.setId(id);
        locatario.setNome(nome);
        locatario.setDataDeNascimento(dataDeNascimento);
        locatario.setCpf(cpfTratado);
        locatario.setSexo(sexo);
        locatario.setTelefone(telefone);
        locatario.setEmail(email);
        return locatario;
    }

}
