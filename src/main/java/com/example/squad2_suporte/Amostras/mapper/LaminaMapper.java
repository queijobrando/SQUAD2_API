package com.example.squad2_suporte.Amostras.mapper;

import com.example.squad2_suporte.Lamina.Lamina;
import com.example.squad2_suporte.dto.lamina.LaminaDto;
import com.example.squad2_suporte.dto.lamina.RetornoLaminaDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LaminaMapper {

    Lamina dtoParaEntidade(LaminaDto laminaDto);

    RetornoLaminaDto entidadeParaRetorno(Lamina lamina);
}
