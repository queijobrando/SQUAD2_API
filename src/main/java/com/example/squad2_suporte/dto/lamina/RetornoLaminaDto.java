package com.example.squad2_suporte.dto.lamina;

import com.example.squad2_suporte.dto.lote.LoteProtocoloDto;
import com.example.squad2_suporte.enuns.StatusAmostra;
import java.time.LocalDateTime;

public record RetornoLaminaDto(
    String protocolo,
    LocalDateTime data,
    StatusAmostra status,
    Integer numeroOvos,
    String resultado,
    LoteProtocoloDto lote,
    String enderecoCaptura // Novo campo
) {}