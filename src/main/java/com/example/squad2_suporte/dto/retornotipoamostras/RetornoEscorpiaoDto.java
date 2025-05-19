package com.example.squad2_suporte.dto.retornotipoamostras;

import com.example.squad2_suporte.dto.amostra.EnderecoDto;
import com.example.squad2_suporte.enuns.StatusAmostra;

import java.time.LocalDateTime;

public record RetornoEscorpiaoDto(Long protocolo, StatusAmostra status, LocalDateTime dataHora, EnderecoDto enderecoDto, Integer quantidade , boolean sofreuAcidente) {
}
