package com.example.squad2_suporte.dto.retornotipoamostras;

import com.example.squad2_suporte.dto.amostra.EnderecoDto;
import com.example.squad2_suporte.dto.lote.LoteProtocoloDto;
import com.example.squad2_suporte.enuns.StatusAmostra;
import com.example.squad2_suporte.enuns.TipoAmostra;
import com.example.squad2_suporte.enuns.flebotomineos.*;
import com.example.squad2_suporte.lote.Lote;

import java.time.LocalDateTime;

public record RetornoFlebotomineosDto(
        String protocolo,
        Long id,
        LoteProtocoloDto lote,
        TipoAmostra tipoAmostra,
        StatusAmostra status,
        LocalDateTime dataHora,
        EnderecoDto enderecoDto,
        ClassificacaoAreaLT classificacaoAreaLT,
        ClassificacaoAreaLV classificacaoAreaLV,
        TipoAtividadeLT tipoAtividadeLT,
        TipoAtividadeLV tipoAtividadeLV,
        TipoVegetacao tipoVegetacao,
        Integer distanciaVegetacao,
        String temperaturaChegada,
        String temperaturaSaida,
        String temperaturaMax,
        String temperaturaMin,
        String umidadeChegada,
        String umidadeSaida,
        String umidadeMax,
        String umidadeMin,
        FasesLua faseLua,
        Vento vento,
        PresencaAnimalIntra presencaAnimalIntra,
        PresencaAnimalPeri presencaAnimalPeri,
        Galinheiro galinheiro,
        Estacao estacaoAno,
        MateriaOrganica materiaOrganica,
        Precipitacao precipitacao,
        String observacao) implements RetornoIdAmostras {
}
