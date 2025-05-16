package com.example.squad2_suporte.Amostras.mapper;

import com.example.squad2_suporte.Classes.Larvas;
import com.example.squad2_suporte.dto.amostra.AmostraDto;
import com.example.squad2_suporte.dto.enviotipoamostras.LarvasDto;
import com.example.squad2_suporte.dto.retornotipoamostras.RetornoLarvasDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public interface LarvasMapper {

    @Mapping(target = "tipoAmostra", constant = "LARVAS")
    @Mapping(target = "dataHora", source = "dataHora") // herança
    @Mapping(target = "endereco", source = "enderecoDto") // usando EnderecoMapper
    Larvas dtoParaEntidade(LarvasDto larvasDto);

    @Mapping(target = "enderecoDto", source = "endereco")
    RetornoLarvasDto entidadeParaRetorno(Larvas larvas);

    LarvasDto fromAmostraDto(AmostraDto amostraDto);

}
