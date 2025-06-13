package com.example.squad2_suporte.Amostras.mapper;

import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.Classes.*;
import com.example.squad2_suporte.dto.amostra.AmostraDto;
import com.example.squad2_suporte.dto.amostra.ProtocoloAmostraDto;
import com.example.squad2_suporte.dto.amostra.ProtocoloListaAmostraDto;
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
    Escorpioes amostraDtoParaEscorpiao(AmostraDto dto);

    @Mapping(target = "enderecoDto", source = "endereco")
    @Mapping(target = "lote", source = "lote", qualifiedByName = "mapearLoteParaProtocolo")
    RetornoEscorpiaoDto escorpiaoEntidadeParaRetorno(Escorpioes escorpioes);


    //Flebotomineos
    @Mapping(target = "tipoAmostra", constant = "FLEBOTOMINEOS")
    @Mapping(target = "dataHora", source = "dataHora", qualifiedByName = "removerSegundos") // herança
    @Mapping(target = "endereco", source = "enderecoDto") // usando EnderecoMapper
    Flebotomineos amostraDtoParaFlebotomineos(AmostraDto dto);

    @Mapping(target = "enderecoDto", source = "endereco")
    @Mapping(target = "lote", source = "lote", qualifiedByName = "mapearLoteParaProtocolo")
    RetornoFlebotomineosDto flebotomineosEntidadeParaRetorno(Flebotomineos flebotomineos);


    // Triatomineos
@Mapping(target = "tipoAmostra", constant = "TRIATOMINEOS")
@Mapping(target = "dataHora", source = "dataHora", qualifiedByName = "removerSegundos") // herança
@Mapping(target = "endereco", source = "enderecoDto") // usando EnderecoMapper
Triatomineos amostraDtoParaTriatomineos(AmostraDto dto);

@Mapping(target = "enderecoDto", source = "endereco")
@Mapping(target = "lote", source = "lote", qualifiedByName = "mapearLoteParaProtocolo")
RetornoTriatomineosDto triatomineosEntidadeParaRetorno(Triatomineos triatomineos); 

    //Molusco
    @Mapping(target = "tipoAmostra", constant = "MOLUSCO")
    @Mapping(target = "dataHora", source = "dataHora", qualifiedByName = "removerSegundos") // herança
    @Mapping(target = "endereco", source = "enderecoDto") // usando EnderecoMapper
    Molusco amostraDtoParaMolusco(AmostraDto dto);

    @Mapping(target = "enderecoDto", source = "endereco")
    @Mapping(target = "lote", source = "lote", qualifiedByName = "mapearLoteParaProtocolo")
    RetornoMoluscoDto moluscoEntidadeParaRetorno(Molusco molusco);


    //Larvas
    @Mapping(target = "tipoAmostra", constant = "LARVAS")
    @Mapping(target = "dataHora", source = "dataHora", qualifiedByName = "removerSegundos") // herança
    @Mapping(target = "endereco", source = "enderecoDto") // usando EnderecoMapper
    Larvas amostraDtoParaLarva(AmostraDto dto);

    @Mapping(target = "enderecoDto", source = "endereco")
    @Mapping(target = "lote", source = "lote", qualifiedByName = "mapearLoteParaProtocolo")
    RetornoLarvasDto larvasEntidadeParaRetorno(Larvas larvas);

    @Mapping(target = "lote", source = "lote", qualifiedByName = "mapearLoteParaProtocolo")
    ProtocoloListaAmostraDto listagemAmostras(Amostra amostra);


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
