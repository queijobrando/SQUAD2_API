package com.example.squad2_suporte.dto.lote;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record ProtocolosDto(
        @NotEmpty(message = "É necessário ao menos um protocolo")
        List<String> protocolos
) {
}