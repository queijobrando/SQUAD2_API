package com.example.squad2_suporte.dto.lote;

import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.enuns.StatusLote;

import java.time.LocalDateTime;
import java.util.List;

public record RetornoLoteDto(Long id, Long protocolo, StatusLote statusLote, LocalDateTime dataCriacao, List<Amostra> amostras) {
}
