package com.example.squad2_suporte.dto.enviotipoamostras;

import com.example.squad2_suporte.dto.amostra.EnderecoDto;
import com.example.squad2_suporte.enuns.larva.TipoLarva;

import java.time.LocalDateTime;

public record LarvasDto(
        LocalDateTime dataHora,
        EnderecoDto enderecoDto,
        Integer numLarvas,
        TipoLarva tipoLarva,
        String tipoImovel
) {
}
