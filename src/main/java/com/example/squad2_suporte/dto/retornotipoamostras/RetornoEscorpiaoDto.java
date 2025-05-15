package com.example.squad2_suporte.dto.retornotipoamostras;

import com.example.squad2_suporte.dto.amostra.EnderecoDto;

import java.time.LocalDateTime;

public record RetornoEscorpiaoDto(Long id, LocalDateTime dataHora, EnderecoDto enderecoDto, Integer quantidade , boolean sofreuAcidente) {
}
