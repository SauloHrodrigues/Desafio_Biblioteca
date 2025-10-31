package com.db.projeto.gerenciamento_de_biblioteca.mappers;

import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AtualizacaoAutorDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.AutorResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.autor.NovoAutorDto;
import com.db.projeto.gerenciamento_de_biblioteca.model.Autor;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutorMapper {
    AutorMapper INSTANCE = Mappers.getMapper(AutorMapper.class);

    @Mapping(target = "nome", expression = "java(dto.nome() != null ? dto.nome().toLowerCase() : null)")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "livros", ignore = true)
    Autor toEntity(NovoAutorDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "livros", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Autor update(@MappingTarget Autor autor, AtualizacaoAutorDto atuaalizacao);
    AutorResponseDto toResponse(Autor autor);
}