package com.example.squad2_suporte.Amostras.mapper;

import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.Lamina.Lamina;
import com.example.squad2_suporte.dto.amostra.ProtocoloAmostraDto;
import com.example.squad2_suporte.dto.lamina.LoteLaminaProtocoloDto;
import com.example.squad2_suporte.dto.lamina.RetornoLaminaDto;
import com.example.squad2_suporte.dto.lote.RetornoLoteDto;
import com.example.squad2_suporte.lote.Lote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoteMapper {

    @Mapping(target = "protocoloAmostras", source = "amostras", qualifiedByName = "mapearProtocolos")
    @Mapping(target = "protocoloLaminas", source = "laminas", qualifiedByName = "mapearProtocolosLaminas")
    RetornoLoteDto entidadeParaRetorno(Lote lote);

    @Named("mapearProtocolos")
    default List<ProtocoloAmostraDto> mapearProtocolos(List<Amostra> amostras) {
        if (amostras == null) {
            return List.of(); // ou Collections.emptyList();
        } else {
            return amostras.stream()
                    .map(a -> new ProtocoloAmostraDto(a.getProtocolo(), a.getTipoAmostra(), a.getStatus()))
                    .toList();
        }
    }

    @Named("mapearProtocolosLaminas")
    default List<LoteLaminaProtocoloDto> mapearProtocolosLaminas(List<Lamina> laminas) {
        if (laminas == null) return List.of();
        return laminas.stream()
                .map(l -> new LoteLaminaProtocoloDto(l.getProtocolo(), l.getStatus()))
                .toList();
    }
}
