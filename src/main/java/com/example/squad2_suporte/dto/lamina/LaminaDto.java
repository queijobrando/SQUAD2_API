package com.example.squad2_suporte.dto.lamina;

import jakarta.validation.constraints.Min;

public record LaminaDto(
    @Min(value = 0, message = "O número de ovos não pode ser negativo")
    Integer numeroOvos,
    String resultado,
    String enderecoCaptura
) {}