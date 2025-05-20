package com.example.squad2_suporte.Amostras.mapper;

import com.example.squad2_suporte.dto.lote.RetornoLoteDto;
import com.example.squad2_suporte.lote.Lote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoteMapper {

    RetornoLoteDto entidadeParaRetorno(Lote lote);
}
