package com.db.projeto.gerenciamento_de_biblioteca.mappers;

import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroAtualizacoesDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.LivroResponseDto;
import com.db.projeto.gerenciamento_de_biblioteca.dto.livro.NovoLivroDto;
import com.db.projeto.gerenciamento_de_biblioteca.model.Autor;
import com.db.projeto.gerenciamento_de_biblioteca.model.Livro;
import java.util.Collection;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface LivroMapper {
    LivroMapper INSTANCE = Mappers.getMapper(LivroMapper.class);

    @Mapping(target = "titulo", expression = "java(dto.titulo() != null ? dto.titulo().toLowerCase() : null)")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "DISPONIVEL") // opcional: valor padrão
    @Mapping(target = "autores", source = "autores")
    Livro toEntity(NovoLivroDto dto, Collection<Autor> autores);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "autores", ignore = true)
    @Mapping(target = "aluguel", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Livro update(@MappingTarget Livro livro, LivroAtualizacoesDto atuaalizacao);

    @Mapping(target = "idAutores",
            expression = "java(livro.getAutores() != null ? " +
                    "livro.getAutores().stream().map(Autor::getId).toList() : null)")
    LivroResponseDto toResponse(Livro livro);

}