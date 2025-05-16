package com.example.squad2_suporte.dto.amostra;

import com.example.squad2_suporte.enuns.Resultado;
import com.example.squad2_suporte.enuns.TipoAmostra;
import com.example.squad2_suporte.enuns.flebotomineos.*;
import com.example.squad2_suporte.enuns.larva.TipoLarva;
import com.example.squad2_suporte.enuns.molusco.TipoMolusco;
import com.example.squad2_suporte.enuns.triatomineos.*;

import java.time.LocalDateTime;

public record AmostraDto(
        TipoAmostra tipoAmostra,
        LocalDateTime dataHora,
        EnderecoDto enderecoDto,
        Integer quantidade,
        boolean sofreuAcidente,
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
        String observacao,
        Peridomicilio peridomicilio,
        Intradomicilio intradomicilio,
        String comodoCaptura,
        Vestigios vestigios,
        Insetifugo insetifugo,
        Integer numeroInsetos,
        Condicao condicao,
        String colecaoHidrica,
        Integer numMoluscos,
        TipoMolusco tipoMolusco,
        Integer numMortos,
        boolean exposicaoLuz,
        boolean esmagamentoConcha,
        boolean disseccao,
        Resultado resultado,
        Integer numLarvas,
        TipoLarva tipoLarva,
        String tipoImovel) {
}
