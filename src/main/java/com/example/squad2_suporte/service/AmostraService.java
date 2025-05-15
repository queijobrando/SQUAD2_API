package com.example.squad2_suporte.service;

import com.example.squad2_suporte.Amostras.mapper.EscorpiaoMapper;
import com.example.squad2_suporte.Amostras.mapper.FlebotomineosMapper;
import com.example.squad2_suporte.Amostras.mapper.TriatomineosMapper;
import com.example.squad2_suporte.Classes.Escorpioes;
import com.example.squad2_suporte.Classes.Flebotomineos;
import com.example.squad2_suporte.Classes.Triatomineos;
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
    public RetornoEscorpiaoDto cadastrarAmostraEscorpioes (EscorpiaoDto dados){
        System.out.println("rCEBENDO "+ dados.dataHora());
        Escorpioes novaAmostra = escorpiaoMapper.dtoParaEntidade(dados);
        System.out.println(novaAmostra.getDataHora());
        amostraEscorpiaoRepository.save(novaAmostra);

        return escorpiaoMapper.entidadeParaRetorno(novaAmostra);
    }

    @Transactional
    public RetornoFlebotomineosDto cadastrarAmostraFlebotomineos (FlebotomineosDto dados){
        Flebotomineos novaAmostra = flebotomineoMapper.dtoParaEntidade(dados);
        amostraFlebotomineosRepository.save(novaAmostra);

        return flebotomineoMapper.entidadeParaRetorno(novaAmostra);
    }

    @Transactional
    public RetornoTriatomineosDto cadastrarAmostraTriatomineos (TriatomineosDto dados){
        Triatomineos novaAmostra = triatomineoMapper.dtoParaEntidade(dados);
        amostraTriatomineosRepository.save(novaAmostra);

        return triatomineoMapper.entidadeParaRetorno(novaAmostra);
    }

}
