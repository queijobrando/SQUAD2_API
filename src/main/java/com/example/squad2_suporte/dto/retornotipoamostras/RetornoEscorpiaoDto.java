package com.example.squad2_suporte.dto.retornotipoamostras;

import com.example.squad2_suporte.dto.amostra.EnderecoDto;
import com.example.squad2_suporte.dto.lote.LoteProtocoloDto;
import com.example.squad2_suporte.enuns.StatusAmostra;
import com.example.squad2_suporte.enuns.TipoAmostra;
import com.example.squad2_suporte.lote.Lote;

import java.time.LocalDateTime;

public record RetornoEscorpiaoDto(String protocolo, Long id, LoteProtocoloDto lote, TipoAmostra tipoAmostra, StatusAmostra status, LocalDateTime dataHora, EnderecoDto enderecoDto, Integer quantidade , boolean sofreuAcidente) implements RetornoIdAmostras {
}
