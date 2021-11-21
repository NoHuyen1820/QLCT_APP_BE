package com.qlct.core.mapper;


import com.qlct.core.dto.TypeDTO;
import com.qlct.model.Type;
import org.mapstruct.Mapper;


@Mapper(componentModel = "jsr330")
public interface TypeMapper extends EntityMapper<Type, TypeDTO> {


    @Override
    Type toEntity(TypeDTO dto);

    @Override
    TypeDTO toDto(Type entity);
}
