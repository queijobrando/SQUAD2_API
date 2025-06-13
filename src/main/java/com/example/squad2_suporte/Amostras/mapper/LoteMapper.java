package com.example.squad2_suporte.Amostras.mapper;

import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.Lamina.Lamina;
import com.example.squad2_suporte.dto.amostra.ProtocoloAmostraDto;
import com.example.squad2_suporte.dto.lamina.LoteLaminaProtocoloDto;
import com.example.squad2_suporte.dto.lote.RetornoLoteDto;
import com.example.squad2_suporte.lote.Lote;
import com.example.squad2_suporte.enuns.TipoLote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoteMapper {

    @Mapping(target = "tipo", source = "tipo")
    @Mapping(target = "protocoloAmostras", expression = "java(lote.getTipo() == TipoLote.AMOSTRA ? mapearSomenteAmostras(lote) : null)")
    @Mapping(target = "protocoloLaminas", expression = "java(lote.getTipo() == TipoLote.LAMINA ? mapearSomenteLaminas(lote) : null)")
    RetornoLoteDto entidadeParaRetorno(Lote lote);

    default List<ProtocoloAmostraDto> mapearSomenteAmostras(Lote lote) {
        if (lote.getAmostras() != null && !lote.getAmostras().isEmpty()) {
            return lote.getAmostras().stream()
                    .map(a -> new ProtocoloAmostraDto(a.getProtocolo(), a.getTipoAmostra(), a.getStatus()))
                    .toList();
        }
        return null;
    }

    default List<LoteLaminaProtocoloDto> mapearSomenteLaminas(Lote lote) {
        if (lote.getLaminas() != null && !lote.getLaminas().isEmpty()) {
            return lote.getLaminas().stream()
                    .map(l -> new LoteLaminaProtocoloDto(l.getProtocolo(), l.getStatus()))
                    .toList();
        }
        return null;
    }
}