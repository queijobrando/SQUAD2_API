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
    private EscorpiaoMapper escorpiaoMapper;

    @Autowired
    private FlebotomineosMapper flebotomineoMapper;

    @Autowired
    private TriatomineosMapper triatomineoMapper;

    @Autowired
    private MoluscoMapper moluscoMapper;

    @Autowired
    private LarvasMapper larvasMapper;

    @Transactional
    public Object cadastrarAmostraUnificada(AmostraDto dto) {
        switch (dto.tipoAmostra()) {
            case ESCORPIAO -> {
                EscorpiaoDto escorpiaoDto = escorpiaoMapper.fromAmostraDto(dto);
                Escorpioes escorpiao = escorpiaoMapper.dtoParaEntidade(escorpiaoDto);
                amostraEscorpiaoRepository.save(escorpiao);
                return escorpiaoMapper.entidadeParaRetorno(escorpiao);
            }
            case FLEBOTOMINEOS -> {
                FlebotomineosDto flebotomineosDto = flebotomineoMapper.fromAmostraDto(dto);
                Flebotomineos flebo = flebotomineoMapper.dtoParaEntidade(flebotomineosDto);
                amostraFlebotomineosRepository.save(flebo);
                return flebotomineoMapper.entidadeParaRetorno(flebo);
            }
            case TRIATOMINEOS -> {
                TriatomineosDto triatoDto = triatomineoMapper.fromAmostraDto(dto);
                Triatomineos triato = triatomineoMapper.dtoParaEntidade(triatoDto);
                amostraTriatomineosRepository.save(triato);
                return triatomineoMapper.entidadeParaRetorno(triato);
            }
            case MOLUSCO -> {
                MoluscoDto moluscoDto = moluscoMapper.fromAmostraDto(dto);
                Molusco molusco = moluscoMapper.dtoParaEntidade(moluscoDto);
                amostraMoluscoRepository.save(molusco);
                return moluscoMapper.entidadeParaRetorno(molusco);
            }
            case LARVAS -> {
                LarvasDto larvasDto = larvasMapper.fromAmostraDto(dto);
                Larvas larvas = larvasMapper.dtoParaEntidade(larvasDto);
                amostraLarvaRepository.save(larvas);
                return larvasMapper.entidadeParaRetorno(larvas);
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
                    return escorpiaoMapper.entidadeParaRetorno(escorpioes);
                }
                case FLEBOTOMINEOS -> {
                    Flebotomineos flebo = amostraFlebotomineosRepository.findByProtocolo(protocolo);
                    return flebotomineoMapper.entidadeParaRetorno(flebo);
                }
                case TRIATOMINEOS -> {
                    Triatomineos triato = amostraTriatomineosRepository.findByProtocolo(protocolo);
                    return triatomineoMapper.entidadeParaRetorno(triato);
                }
                case LARVAS -> {
                    Larvas larvas = amostraLarvaRepository.findByProtocolo(protocolo);
                    return larvasMapper.entidadeParaRetorno(larvas);
                }
                case MOLUSCO -> {
                    Molusco molusco = amostraMoluscoRepository.findByProtocolo(protocolo);
                    return moluscoMapper.entidadeParaRetorno(molusco);
                }
                default -> throw new IllegalArgumentException("Amostra não encontrada");
            }
        }

    }


}
