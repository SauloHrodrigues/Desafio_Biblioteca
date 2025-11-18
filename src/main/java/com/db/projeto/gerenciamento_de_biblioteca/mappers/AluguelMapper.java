package com.db.projeto.gerenciamento_de_biblioteca.mappers;

import com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel.AluguelResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel.NovoAluguel;
import com.db.projeto.gerenciamento_de_biblioteca.dto.aluguel.NovoAluguelDto;
import com.db.projeto.gerenciamento_de_biblioteca.model.Aluguel;
import com.db.projeto.gerenciamento_de_biblioteca.model.Livro;
import com.db.projeto.gerenciamento_de_biblioteca.model.Locatario;
import java.time.LocalDate;
import java.util.Set;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AluguelMapper {
    AluguelMapper INSTANCE = Mappers.getMapper(AluguelMapper.class);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "devolvido", constant = "false")
    @Mapping(target = "locatario", ignore = true)
    @Mapping(target = "livros", expression = "java(new java.util.ArrayList<>())")
    @Mapping(target = "retirada", expression = "java(definirDataRetirada(dto.retirada()))")
    @Mapping(target = "devolucao", expression = "java(definirDataDaDevolucao(dto.retirada(),dto.devolucao()))")
    Aluguel toEntity(NovoAluguelDto dto);

    AluguelResponseDto toResponse(Aluguel aluguel);

    default LocalDate definirDataRetirada(LocalDate retirada) {
        return retirada == null ? LocalDate.now() : retirada  ;
    }

    default LocalDate definirDataDaDevolucao(LocalDate retirada,LocalDate devolucao) {
        LocalDate retiradaDefinida = retirada == null ? LocalDate.now() : retirada;
        LocalDate devolucaoCalculada = devolucao  == null ? retiradaDefinida.plusDays(2L) : devolucao;
        return devolucaoCalculada;
    }
}