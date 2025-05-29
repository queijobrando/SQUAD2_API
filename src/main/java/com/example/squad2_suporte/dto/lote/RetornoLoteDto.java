package com.example.squad2_suporte.dto.lote;

import com.example.squad2_suporte.dto.amostra.ProtocoloAmostraDto;
import com.example.squad2_suporte.dto.lamina.LoteLaminaProtocoloDto;
import com.example.squad2_suporte.enuns.StatusLote;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY) // Não retorn o campo se estiver vzio
public record RetornoLoteDto(
        Long id,
        Long protocolo,
        StatusLote statusLote,
        LocalDateTime dataCriacao,
        List<ProtocoloAmostraDto> protocoloAmostras,
        List<LoteLaminaProtocoloDto> protocoloLaminas) {
}
