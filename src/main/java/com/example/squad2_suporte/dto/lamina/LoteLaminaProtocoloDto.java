package com.example.squad2_suporte.dto.lamina;

import com.example.squad2_suporte.enuns.StatusAmostra;

public record LoteLaminaProtocoloDto(
        Long protocolo,
        StatusAmostra status
) {
}
