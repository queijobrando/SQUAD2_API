package com.example.squad2_suporte.dto.retornotipoamostras;

import com.example.squad2_suporte.dto.amostra.EnderecoDto;
import com.example.squad2_suporte.enuns.StatusAmostra;
import com.example.squad2_suporte.enuns.TipoAmostra;

import java.time.LocalDateTime;

public record RetornoEscorpiaoDto(Long protocolo, Long id, TipoAmostra tipoAmostra, StatusAmostra status, LocalDateTime dataHora, EnderecoDto enderecoDto, Integer quantidade , boolean sofreuAcidente) implements RetornoIdAmostras {
}
