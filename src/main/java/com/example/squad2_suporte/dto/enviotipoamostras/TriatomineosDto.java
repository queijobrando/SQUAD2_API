package com.example.squad2_suporte.dto.enviotipoamostras;

import com.example.squad2_suporte.dto.amostra.EnderecoDto;
import com.example.squad2_suporte.enuns.triatomineos.*;

import java.time.LocalDateTime;

public record TriatomineosDto(LocalDateTime dataHora,
                                     EnderecoDto enderecoDto,
                                     Peridomicilio peridomicilio,
                                     Intradomicilio intradomicilio,
                                     String comodoCaptura,
                                     Vestigios vestigios,
                                     Insetifugo insetifugo,
                                     Integer numeroInsetos,
                                     Condicao condicao) {
}
