package com.example.squad2_suporte.dto.enviotipoamostras;

import com.example.squad2_suporte.dto.amostra.EnderecoDto;

import java.time.LocalDateTime;

public record EscorpiaoDto(LocalDateTime dataHora, EnderecoDto enderecoDto, Integer quantidade , boolean sofreuAcidente) {
}
