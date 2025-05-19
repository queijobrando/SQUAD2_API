package com.example.squad2_suporte.Amostras.mapper;

import com.example.squad2_suporte.Classes.Molusco;
import com.example.squad2_suporte.dto.amostra.AmostraDto;
import com.example.squad2_suporte.dto.enviotipoamostras.MoluscoDto;
import com.example.squad2_suporte.dto.retornotipoamostras.RetornoMoluscoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public interface MoluscoMapper {

    @Mapping(target = "tipoAmostra", constant = "MOLUSCO")
    @Mapping(target = "dataHora", source = "dataHora", qualifiedByName = "removerSegundos") // herança
    @Mapping(target = "endereco", source = "enderecoDto") // usando EnderecoMapper
    Molusco dtoParaEntidade(MoluscoDto moluscoDto);

    @Mapping(target = "enderecoDto", source = "endereco")
    RetornoMoluscoDto entidadeParaRetorno(Molusco molusco);

    MoluscoDto fromAmostraDto(AmostraDto amostraDto);

    @Named("removerSegundos")
    static LocalDateTime removerMiliSegundos(LocalDateTime data) {
        return data != null ? data.truncatedTo(ChronoUnit.MINUTES) : null;
    }
}
