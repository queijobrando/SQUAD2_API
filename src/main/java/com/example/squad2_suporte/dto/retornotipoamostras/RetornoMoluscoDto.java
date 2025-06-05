package com.example.squad2_suporte.dto.retornotipoamostras;

import com.example.squad2_suporte.dto.amostra.EnderecoDto;
import com.example.squad2_suporte.dto.lote.LoteProtocoloDto;
import com.example.squad2_suporte.enuns.Resultado;
import com.example.squad2_suporte.enuns.StatusAmostra;
import com.example.squad2_suporte.enuns.TipoAmostra;
import com.example.squad2_suporte.enuns.molusco.TipoMolusco;

import java.time.LocalDateTime;

public record RetornoMoluscoDto(
        String protocolo,
        TipoAmostra tipoAmostra,
        Long id,
        LoteProtocoloDto lote,
        StatusAmostra status,
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
) implements RetornoIdAmostras {
}
