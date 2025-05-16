package com.example.squad2_suporte.dto.retornotipoamostras;

import com.example.squad2_suporte.dto.amostra.EnderecoDto;
import com.example.squad2_suporte.enuns.Resultado;
import com.example.squad2_suporte.enuns.molusco.TipoMolusco;

import java.time.LocalDateTime;

public record RetornoMoluscoDto(
        Long id,
        LocalDateTime dataHora,
        EnderecoDto enderecoDto,
        String colecaoHidrica,
        Integer numMoluscos,
        TipoMolusco tipoMolusco,
        Integer numMortos,
        boolean exposicaoLuz,
        boolean esmagamentoConcha,
        boolean disseccao,
        Resultado resultado
) {
}
