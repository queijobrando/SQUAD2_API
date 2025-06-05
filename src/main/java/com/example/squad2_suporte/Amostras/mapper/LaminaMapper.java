package com.example.squad2_suporte.Amostras.mapper;

import com.example.squad2_suporte.Lamina.Lamina;
import com.example.squad2_suporte.dto.lamina.LaminaDto;
import com.example.squad2_suporte.dto.lamina.RetornoLaminaDto;
import com.example.squad2_suporte.dto.lote.LoteProtocoloDto;
import com.example.squad2_suporte.lote.Lote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface LaminaMapper {

    @Mapping(target = "enderecoCaptura", source = "enderecoCaptura")
    @Mapping(target = "baseLegal", ignore = true) // Será tratado no serviço
    Lamina dtoParaEntidade(LaminaDto laminaDto);

    @Mapping(target = "lote", source = "lote", qualifiedByName = "mapearLoteParaProtocolo")
    @Mapping(target = "enderecoCaptura", source = "enderecoCaptura")
    RetornoLaminaDto entidadeParaRetorno(Lamina lamina);

    @Named("mapearLoteParaProtocolo")
    static LoteProtocoloDto mapearLoteParaProtocolo(Lote lote) {
        return lote != null ? new LoteProtocoloDto(lote.getProtocolo()) : null;
    }
}