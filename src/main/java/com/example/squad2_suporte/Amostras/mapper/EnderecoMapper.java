package com.example.squad2_suporte.Amostras.mapper;

import com.example.squad2_suporte.Amostras.endereco.Endereco;
import com.example.squad2_suporte.dto.amostra.EnderecoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {
    Endereco toEntity(EnderecoDto dto);

    EnderecoDto toDto(Endereco endereco);
}
