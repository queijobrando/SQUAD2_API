package com.example.squad2_suporte.Amostras.mapper;

import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.dto.amostra.ProtocoloAmostraDto;
import com.example.squad2_suporte.dto.lote.RetornoLoteDto;
import com.example.squad2_suporte.lote.Lote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoteMapper {

    @Mapping(target = "protocoloAmostras", source = "amostras", qualifiedByName = "mapearProtocolos")
    RetornoLoteDto entidadeParaRetorno(Lote lote);

    @Named("mapearProtocolos")
    default List<ProtocoloAmostraDto> mapearProtocolos(List<Amostra> amostras) {
        if (amostras == null) {
            return List.of(); // ou Collections.emptyList();
        } else {
            return amostras.stream()
                    .map(a -> new ProtocoloAmostraDto(a.getProtocolo(), a.getTipoAmostra()))
                    .toList();
        }
    }
}
