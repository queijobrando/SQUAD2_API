package com.example.squad2_suporte.dto.lote;

import com.example.squad2_suporte.enuns.TipoLote;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record LoteDto(
        @NotNull(message = "O tipo do lote é obrigatório")
        TipoLote tipo,
        @NotEmpty(message = "É necessário ao menos um protocolo")
        List<String> protocolos
) {
}