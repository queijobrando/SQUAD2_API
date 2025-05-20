package com.example.squad2_suporte.dto.lamina;

import java.time.LocalDateTime;

public record RetornoLaminaDto(Long id, Long protocolo, LocalDateTime data, Integer numeroOvos, String resultado) {
}
