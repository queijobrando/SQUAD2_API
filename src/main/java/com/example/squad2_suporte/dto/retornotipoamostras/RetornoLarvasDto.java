package com.example.squad2_suporte.dto.retornotipoamostras;

import com.example.squad2_suporte.dto.amostra.EnderecoDto;
import com.example.squad2_suporte.enuns.StatusAmostra;
import com.example.squad2_suporte.enuns.larva.TipoLarva;

import java.time.LocalDateTime;

public record RetornoLarvasDto(
        Long protocolo,
        StatusAmostra status,
        LocalDateTime dataHora,
        EnderecoDto enderecoDto,
        Integer numLarvas,
        TipoLarva tipoLarva,
        String tipoImovel
) {
}
