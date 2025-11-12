package com.db.projeto.gerenciamento_de_biblioteca.mappers;

import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.LocatarioAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.LocatarioResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.locatario.NovoLocatarioDto;
import com.db.projeto.gerenciamento_de_biblioteca.model.Locatario;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LocatarioMapper {
    LocatarioMapper INSTANCE = Mappers.getMapper(LocatarioMapper.class);

    @Mapping(target = "nome", expression = "java(dto.nome() != null ? dto.nome().toLowerCase() : null)")
    @Mapping(target = "email", expression = "java(dto.email() != null ? dto.email().toLowerCase() : null)")
    Locatario toEntity(NovoLocatarioDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "alugueis", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Locatario update(@MappingTarget Locatario locatario, LocatarioAtualizacoesDto atualizacao);
    LocatarioResponseDto toResponseDto(Locatario locatario);
    List<LocatarioResponseDto> toResponseDto(List<Locatario> autores);
}