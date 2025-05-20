package com.example.squad2_suporte.Amostras.mapper;

import com.example.squad2_suporte.Classes.*;
import com.example.squad2_suporte.dto.amostra.AmostraDto;
import com.example.squad2_suporte.dto.enviotipoamostras.*;
import com.example.squad2_suporte.dto.lote.LoteProtocoloDto;
import com.example.squad2_suporte.dto.retornotipoamostras.*;
import com.example.squad2_suporte.lote.Lote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Mapper(componentModel = "spring", uses = {EnderecoMapper.class})
public interface TipoAmostraMapper {

    // Escorpiao
    @Mapping(target = "tipoAmostra", constant = "ESCORPIAO")
    @Mapping(target = "dataHora", source = "dataHora", qualifiedByName = "removerSegundos") // herança
    @Mapping(target = "endereco", source = "enderecoDto") // usando EnderecoMapper
    Escorpioes escorpiaoDtoParaEntidade(EscorpiaoDto dto);

    @Mapping(target = "enderecoDto", source = "endereco")
    @Mapping(target = "lote", source = "lote", qualifiedByName = "mapearLoteParaProtocolo")
    RetornoEscorpiaoDto escorpiaoEntidadeParaRetorno(Escorpioes escorpioes);

    EscorpiaoDto escorpiaoFromAmostraDto(AmostraDto dto);

    //Flebotomineos
    @Mapping(target = "tipoAmostra", constant = "FLEBOTOMINEOS")
    @Mapping(target = "dataHora", source = "dataHora", qualifiedByName = "removerSegundos") // herança
    @Mapping(target = "endereco", source = "enderecoDto") // usando EnderecoMapper
    Flebotomineos flebotomineosDtoParaEntidade(FlebotomineosDto dto);

    @Mapping(target = "enderecoDto", source = "endereco")
    @Mapping(target = "lote", source = "lote", qualifiedByName = "mapearLoteParaProtocolo")
    RetornoFlebotomineosDto flebotomineosEntidadeParaRetorno(Flebotomineos flebotomineos);

    FlebotomineosDto flebotomineosFromAmostraDto(AmostraDto dto);

    //Triatomineos
    @Mapping(target = "tipoAmostra", constant = "TRIATOMINEOS")
    @Mapping(target = "dataHora", source = "dataHora", qualifiedByName = "removerSegundos") // herança
    @Mapping(target = "endereco", source = "enderecoDto") // usando EnderecoMapper
    Triatomineos triatomineosDtoParaEntidade(TriatomineosDto dto);

    @Mapping(target = "enderecoDto", source = "endereco")
    @Mapping(target = "lote", source = "lote", qualifiedByName = "mapearLoteParaProtocolo")
    RetornoTriatomineosDto triatomieosEntidadeParaRetorno(Triatomineos triatomineos);

    TriatomineosDto triatomineosFromAmostraDto(AmostraDto dto);

    //Molusco
    @Mapping(target = "tipoAmostra", constant = "MOLUSCO")
    @Mapping(target = "dataHora", source = "dataHora", qualifiedByName = "removerSegundos") // herança
    @Mapping(target = "endereco", source = "enderecoDto") // usando EnderecoMapper
    Molusco moluscoDtoParaEntidade(MoluscoDto moluscoDto);

    @Mapping(target = "enderecoDto", source = "endereco")
    @Mapping(target = "lote", source = "lote", qualifiedByName = "mapearLoteParaProtocolo")
    RetornoMoluscoDto moluscoEntidadeParaRetorno(Molusco molusco);

    MoluscoDto moluscoFromAmostraDto(AmostraDto amostraDto);

    //Larvas
    @Mapping(target = "tipoAmostra", constant = "LARVAS")
    @Mapping(target = "dataHora", source = "dataHora", qualifiedByName = "removerSegundos") // herança
    @Mapping(target = "endereco", source = "enderecoDto") // usando EnderecoMapper
    Larvas larvasDtoParaEntidade(LarvasDto larvasDto);

    @Mapping(target = "enderecoDto", source = "endereco")
    @Mapping(target = "lote", source = "lote", qualifiedByName = "mapearLoteParaProtocolo")
    RetornoLarvasDto larvasEntidadeParaRetorno(Larvas larvas);

    LarvasDto larvasFromAmostraDto(AmostraDto amostraDto);

    //Metodo converter LocalDateTime
    @Named("removerSegundos")
    static LocalDateTime removerMiliSegundos(LocalDateTime data) {
        return data != null ? data.truncatedTo(ChronoUnit.MINUTES) : null;
    }

    @Named("mapearLoteParaProtocolo")
    static LoteProtocoloDto mapearLoteParaProtocolo(Lote lote) {
        return lote != null ? new LoteProtocoloDto(lote.getProtocolo()) : null;
    }
}
