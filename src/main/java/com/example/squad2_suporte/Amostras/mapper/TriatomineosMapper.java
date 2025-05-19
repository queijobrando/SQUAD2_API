package com.example.squad2_suporte.Amostras.mapper;

import com.example.squad2_suporte.Classes.Escorpioes;
import com.example.squad2_suporte.Classes.Triatomineos;
import com.example.squad2_suporte.dto.amostra.AmostraDto;
import com.example.squad2_suporte.dto.enviotipoamostras.TriatomineosDto;
import com.example.squad2_suporte.dto.retornotipoamostras.RetornoEscorpiaoDto;
import com.example.squad2_suporte.dto.retornotipoamostras.RetornoTriatomineosDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public interface TriatomineosMapper {

    @Mapping(target = "tipoAmostra", constant = "TRIATOMINEOS")
    @Mapping(target = "dataHora", source = "dataHora", qualifiedByName = "removerSegundos") // herança
    @Mapping(target = "endereco", source = "enderecoDto") // usando EnderecoMapper
    Triatomineos dtoParaEntidade(TriatomineosDto dto);

    @Mapping(target = "enderecoDto", source = "endereco")
    RetornoTriatomineosDto entidadeParaRetorno(Triatomineos triatomineos);

    TriatomineosDto fromAmostraDto(AmostraDto dto);

    @Named("removerSegundos")
    static LocalDateTime removerMiliSegundos(LocalDateTime data) {
        return data != null ? data.truncatedTo(ChronoUnit.MINUTES) : null;
    }

}

