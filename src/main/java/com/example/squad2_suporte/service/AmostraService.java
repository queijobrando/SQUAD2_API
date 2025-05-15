package com.example.squad2_suporte.service;

import com.example.squad2_suporte.Amostras.mapper.EscorpiaoMapper;
import com.example.squad2_suporte.Amostras.mapper.FlebotomineosMapper;
import com.example.squad2_suporte.Amostras.mapper.TriatomineosMapper;
import com.example.squad2_suporte.Classes.Escorpioes;
import com.example.squad2_suporte.Classes.Flebotomineos;
import com.example.squad2_suporte.Classes.Triatomineos;
import com.example.squad2_suporte.dto.amostra.AmostraDto;
import com.example.squad2_suporte.dto.enviotipoamostras.EscorpiaoDto;
import com.example.squad2_suporte.dto.enviotipoamostras.FlebotomineosDto;
import com.example.squad2_suporte.dto.enviotipoamostras.TriatomineosDto;
import com.example.squad2_suporte.dto.retornotipoamostras.RetornoEscorpiaoDto;
import com.example.squad2_suporte.dto.retornotipoamostras.RetornoFlebotomineosDto;
import com.example.squad2_suporte.dto.retornotipoamostras.RetornoTriatomineosDto;
import com.example.squad2_suporte.repositorios.AmostraEscorpiaoRepository;
import com.example.squad2_suporte.repositorios.AmostraFlebotomineosRepository;
import com.example.squad2_suporte.repositorios.AmostraRepository;
import com.example.squad2_suporte.repositorios.AmostraTriatomineosRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmostraService {

    @Autowired
    private AmostraRepository amostraRepository;

    @Autowired
    private AmostraEscorpiaoRepository amostraEscorpiaoRepository;

    @Autowired
    private AmostraFlebotomineosRepository amostraFlebotomineosRepository;

    @Autowired
    private AmostraTriatomineosRepository amostraTriatomineosRepository;

    @Autowired
    private EscorpiaoMapper escorpiaoMapper;

    @Autowired
    private FlebotomineosMapper flebotomineoMapper;

    @Autowired
    private TriatomineosMapper triatomineoMapper;

    @Transactional
    public Object cadastrarAmostraUnificada(AmostraDto dto) {
        switch (dto.tipoAmostra()) {
            case ESCORPIAO -> {
                EscorpiaoDto escorpiaoDto = new EscorpiaoDto(
                        dto.dataHora(),
                        dto.enderecoDto(),
                        dto.quantidade(),
                        dto.sofreuAcidente()
                );
                Escorpioes escorpiao = escorpiaoMapper.dtoParaEntidade(escorpiaoDto);
                amostraEscorpiaoRepository.save(escorpiao);
                return escorpiaoMapper.entidadeParaRetorno(escorpiao);
            }
            case FLEBOTOMINEOS -> {
                FlebotomineosDto fleboDto = new FlebotomineosDto(
                        dto.dataHora(),
                        dto.enderecoDto(),
                        dto.classificacaoAreaLT(),
                        dto.classificacaoAreaLV(),
                        dto.tipoAtividadeLT(),
                        dto.tipoAtividadeLV(),
                        dto.tipoVegetacao(),
                        dto.distanciaVegetacao(),
                        dto.temperaturaChegada(),
                        dto.temperaturaSaida(),
                        dto.temperaturaMax(),
                        dto.temperaturaMin(),
                        dto.umidadeChegada(),
                        dto.umidadeSaida(),
                        dto.umidadeMax(),
                        dto.umidadeMin(),
                        dto.faseLua(),
                        dto.vento(),
                        dto.presencaAnimalIntra(),
                        dto.presencaAnimalPeri(),
                        dto.galinheiro(),
                        dto.estacaoAno(),
                        dto.materiaOrganica(),
                        dto.precipitacao(),
                        dto.observacao()
                );
                Flebotomineos flebo = flebotomineoMapper.dtoParaEntidade(fleboDto);
                amostraFlebotomineosRepository.save(flebo);
                return flebotomineoMapper.entidadeParaRetorno(flebo);
            }
            case TRIATOMINEOS -> {
                TriatomineosDto triatoDto = new TriatomineosDto(
                        dto.dataHora(),
                        dto.enderecoDto(),
                        dto.peridomicilio(),
                        dto.intradomicilio(),
                        dto.comodoCaptura(),
                        dto.vestigios(),
                        dto.insetifugo(),
                        dto.numeroInsetos(),
                        dto.condicao()
                );
                Triatomineos triato = triatomineoMapper.dtoParaEntidade(triatoDto);
                amostraTriatomineosRepository.save(triato);
                return triatomineoMapper.entidadeParaRetorno(triato);
            }
            default -> throw new IllegalArgumentException("Tipo de amostra inválido: " + dto.tipoAmostra());
        }
    }


}
