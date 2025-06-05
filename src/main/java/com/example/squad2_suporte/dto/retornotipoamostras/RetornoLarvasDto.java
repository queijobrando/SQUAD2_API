package com.example.squad2_suporte.dto.retornotipoamostras;

import com.example.squad2_suporte.dto.amostra.EnderecoDto;
import com.example.squad2_suporte.dto.lote.LoteProtocoloDto;
import com.example.squad2_suporte.enuns.StatusAmostra;
import com.example.squad2_suporte.enuns.TipoAmostra;
import com.example.squad2_suporte.enuns.larva.TipoLarva;
import com.example.squad2_suporte.lote.Lote;

import java.time.LocalDateTime;

public record RetornoLarvasDto(
        String protocolo,
        TipoAmostra tipoAmostra,
        Long id,
        LoteProtocoloDto lote,
        StatusAmostra status,
        LocalDateTime dataHora,
        EnderecoDto enderecoDto,
        Integer numLarvas,
        TipoLarva tipoLarva,
        String tipoImovel
) implements RetornoIdAmostras{
}
