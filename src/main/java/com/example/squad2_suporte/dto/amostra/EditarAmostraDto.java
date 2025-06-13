package com.example.squad2_suporte.dto.amostra;

import java.time.LocalDateTime;

public record EditarAmostraDto(
    LocalDateTime dataHora,
    EnderecoDto enderecoDto,
    Integer quantidade,
    Boolean sofreuAcidente
) {}