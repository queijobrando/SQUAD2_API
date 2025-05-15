package com.example.squad2_suporte.Amostras.mapper;

import com.example.squad2_suporte.Classes.Escorpioes;
import com.example.squad2_suporte.Classes.Triatomineos;
import com.example.squad2_suporte.dto.enviotipoamostras.TriatomineosDto;
import com.example.squad2_suporte.dto.retornotipoamostras.RetornoEscorpiaoDto;
import com.example.squad2_suporte.dto.retornotipoamostras.RetornoTriatomineosDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public interface TriatomineosMapper {

    @Mapping(target = "tipoAmostra", constant = "TRIATOMINEOS")
    @Mapping(target = "dataHora", source = "dataHora") // herança
    @Mapping(target = "endereco", source = "enderecoDto") // usando EnderecoMapper
    Triatomineos dtoParaEntidade(TriatomineosDto dto);

    @Mapping(target = "enderecoDto", source = "endereco")
    RetornoTriatomineosDto entidadeParaRetorno(Triatomineos triatomineos);

}

