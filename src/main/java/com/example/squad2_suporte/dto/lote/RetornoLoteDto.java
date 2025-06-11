package com.example.squad2_suporte.dto.lote;

import com.example.squad2_suporte.dto.amostra.ProtocoloAmostraDto;
import com.example.squad2_suporte.dto.lamina.LoteLaminaProtocoloDto;
import com.example.squad2_suporte.enuns.StatusLote;
import com.example.squad2_suporte.enuns.TipoLote;

import java.time.LocalDateTime;
import java.util.List;

public record RetornoLoteDto(
        Long id,
        String protocolo,
        TipoLote tipo,
        StatusLote statusLote,
        LocalDateTime dataCriacao,
        List<ProtocoloAmostraDto> protocoloAmostras,
        List<LoteLaminaProtocoloDto> protocoloLaminas
) {
    public RetornoLoteDto {
        if (tipo == TipoLote.AMOSTRA) {
            protocoloLaminas = null;
        } else if (tipo == TipoLote.LAMINA) {
            protocoloAmostras = null;
        }
    }
}