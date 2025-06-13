package com.example.squad2_suporte.dto.amostra;

import jakarta.validation.constraints.NotNull;

public record EnderecoDto(
        String logradouro,
        String bairro,
        String cep,
        String numero,
        String complemento,
        @NotNull(message = "O campo municipio é obrigatório")
        String municipio
) {
}