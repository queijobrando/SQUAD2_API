package com.example.squad2_suporte.dto.retornotipoamostras;

import com.example.squad2_suporte.dto.amostra.EnderecoDto;
import com.example.squad2_suporte.dto.lote.LoteProtocoloDto;
import com.example.squad2_suporte.enuns.StatusAmostra;
import com.example.squad2_suporte.enuns.TipoAmostra;
import com.example.squad2_suporte.enuns.triatomineos.*;
import com.example.squad2_suporte.lote.Lote;

import java.time.LocalDateTime;

public record RetornoTriatomineosDto(String protocolo,
                                     Long id,
                                     LoteProtocoloDto lote,
                                     StatusAmostra status,
                                     TipoAmostra tipoAmostra,
                                     LocalDateTime dataHora,
                                     EnderecoDto enderecoDto,
                                     Peridomicilio peridomicilio,
                                     Intradomicilio intradomicilio,
                                     String comodoCaptura,
                                     Vestigios vestigios,
                                     Insetifugo insetifugo,
                                     Integer numeroInsetos,
                                     Condicao condicao) implements RetornoIdAmostras {
}
