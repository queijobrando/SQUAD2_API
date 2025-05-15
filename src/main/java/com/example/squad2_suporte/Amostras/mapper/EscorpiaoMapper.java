package com.example.squad2_suporte.Amostras.mapper;

import com.example.squad2_suporte.Classes.Escorpioes;
import com.example.squad2_suporte.dto.enviotipoamostras.EscorpiaoDto;
import com.example.squad2_suporte.dto.retornotipoamostras.RetornoEscorpiaoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public interface EscorpiaoMapper {

    @Mapping(target = "tipoAmostra", constant = "ESCORPIAO")
    @Mapping(target = "dataHora", source = "dataHora") // herança
    @Mapping(target = "endereco", source = "enderecoDto") // usando EnderecoMapper
    Escorpioes dtoParaEntidade(EscorpiaoDto dto);

    @Mapping(target = "enderecoDto", source = "endereco")
    RetornoEscorpiaoDto entidadeParaRetorno(Escorpioes escorpioes);
}

