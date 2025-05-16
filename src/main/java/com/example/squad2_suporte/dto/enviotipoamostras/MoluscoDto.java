package com.example.squad2_suporte.dto.enviotipoamostras;

import com.example.squad2_suporte.dto.amostra.EnderecoDto;
import com.example.squad2_suporte.enuns.Resultado;
import com.example.squad2_suporte.enuns.molusco.TipoMolusco;

import java.time.LocalDateTime;

public record MoluscoDto(
        LocalDateTime dataHora,
        EnderecoDto enderecoDto,
        String colecaoHidrica,
        Integer numMoluscos,
        TipoMolusco tipoMolusco,
        Integer numMortos,
        boolean exposicaoLuz,
        boolean esmagamentoConcha,
        boolean disseccao,
        Resultado resultado) {
}
