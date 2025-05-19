package com.example.squad2_suporte.service;

import com.example.squad2_suporte.Amostras.mapper.*;
import com.example.squad2_suporte.Classes.*;
import com.example.squad2_suporte.dto.amostra.AmostraDto;
import com.example.squad2_suporte.dto.enviotipoamostras.*;
import com.example.squad2_suporte.dto.retornotipoamostras.RetornoEscorpiaoDto;
import com.example.squad2_suporte.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private AmostraMoluscoRepository amostraMoluscoRepository;

    @Autowired
    private AmostraLarvaRepository amostraLarvaRepository;

    @Autowired
    private TipoAmostraMapper tipoAmostraMapper;

    @Transactional
    public Object cadastrarAmostraUnificada(AmostraDto dto) {
        switch (dto.tipoAmostra()) {
            case ESCORPIAO -> {
                EscorpiaoDto escorpiaoDto = tipoAmostraMapper.escorpiaoFromAmostraDto(dto);
                Escorpioes escorpiao = tipoAmostraMapper.escorpiaoDtoParaEntidade(escorpiaoDto);
                amostraEscorpiaoRepository.save(escorpiao);
                return tipoAmostraMapper.escorpiaoEntidadeParaRetorno(escorpiao);
            }
            case FLEBOTOMINEOS -> {
                FlebotomineosDto flebotomineosDto = tipoAmostraMapper.flebotomineosFromAmostraDto(dto);
                Flebotomineos flebo = tipoAmostraMapper.flebotomineosDtoParaEntidade(flebotomineosDto);
                amostraFlebotomineosRepository.save(flebo);
                return tipoAmostraMapper.flebotomineosEntidadeParaRetorno(flebo);
            }
            case TRIATOMINEOS -> {
                TriatomineosDto triatoDto = tipoAmostraMapper.triatomineosFromAmostraDto(dto);
                Triatomineos triato = tipoAmostraMapper.triatomineosDtoParaEntidade(triatoDto);
                amostraTriatomineosRepository.save(triato);
                return tipoAmostraMapper.triatomieosEntidadeParaRetorno(triato);
            }
            case MOLUSCO -> {
                MoluscoDto moluscoDto = tipoAmostraMapper.moluscoFromAmostraDto(dto);
                Molusco molusco = tipoAmostraMapper.moluscoDtoParaEntidade(moluscoDto);
                amostraMoluscoRepository.save(molusco);
                return tipoAmostraMapper.moluscoEntidadeParaRetorno(molusco);
            }
            case LARVAS -> {
                LarvasDto larvasDto = tipoAmostraMapper.larvasFromAmostraDto(dto);
                Larvas larvas = tipoAmostraMapper.larvasDtoParaEntidade(larvasDto);
                amostraLarvaRepository.save(larvas);
                return tipoAmostraMapper.larvasEntidadeParaRetorno(larvas);
            }
            default -> throw new IllegalArgumentException("Tipo de amostra inválido: " + dto.tipoAmostra());
        }
    }

    public void deletarAmostra(Long protocolo) {
        var amostra = amostraRepository.findByProtocolo(protocolo);
        if (amostra == null){
            throw new RuntimeException("Protocolo inválido ou inexistente");
        } else {
            amostraRepository.delete(amostra);
        }

    }

    public Object buscarAmostra(Long protocolo){
        var amostra = amostraRepository.findByProtocolo(protocolo);
        if (amostra == null){
            throw new RuntimeException("Protocolo inválido ou inexistente");
        } else {
            switch (amostra.getTipoAmostra()){
                case ESCORPIAO -> {
                    Escorpioes escorpioes = amostraEscorpiaoRepository.findByProtocolo(protocolo);
                    return tipoAmostraMapper.escorpiaoEntidadeParaRetorno(escorpioes);
                }
                case FLEBOTOMINEOS -> {
                    Flebotomineos flebo = amostraFlebotomineosRepository.findByProtocolo(protocolo);
                    return tipoAmostraMapper.flebotomineosEntidadeParaRetorno(flebo);
                }
                case TRIATOMINEOS -> {
                    Triatomineos triato = amostraTriatomineosRepository.findByProtocolo(protocolo);
                    return tipoAmostraMapper.triatomieosEntidadeParaRetorno(triato);
                }
                case LARVAS -> {
                    Larvas larvas = amostraLarvaRepository.findByProtocolo(protocolo);
                    return tipoAmostraMapper.larvasEntidadeParaRetorno(larvas);
                }
                case MOLUSCO -> {
                    Molusco molusco = amostraMoluscoRepository.findByProtocolo(protocolo);
                    return tipoAmostraMapper.moluscoEntidadeParaRetorno(molusco);
                }
                default -> throw new IllegalArgumentException("Amostra não encontrada");
            }
        }

    }


}
