package com.example.squad2_suporte.Amostras.mapper;

import com.example.squad2_suporte.Classes.Flebotomineos;
import com.example.squad2_suporte.dto.enviotipoamostras.FlebotomineosDto;
import com.example.squad2_suporte.dto.retornotipoamostras.RetornoFlebotomineosDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public interface FlebotomineosMapper {

    @Mapping(target = "tipoAmostra", constant = "FLEBOTOMINEOS")
    @Mapping(target = "dataHora", source = "dataHora") // herança
    @Mapping(target = "endereco", source = "enderecoDto") // usando EnderecoMapper
    Flebotomineos dtoParaEntidade(FlebotomineosDto dto);

    @Mapping(target = "enderecoDto", source = "endereco")
    RetornoFlebotomineosDto entidadeParaRetorno(Flebotomineos flebotomineos);
}

