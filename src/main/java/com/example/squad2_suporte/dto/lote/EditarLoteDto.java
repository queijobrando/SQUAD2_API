package com.example.squad2_suporte.dto.lote;

import com.example.squad2_suporte.enuns.EditarAmostra;

import java.util.List;

public record EditarLoteDto(EditarAmostra opcao, List<Long> protocoloAmostras) {
}
