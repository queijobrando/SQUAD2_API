package com.example.squad2_suporte.service;

import com.example.squad2_suporte.Classes.Molusco;
import com.example.squad2_suporte.Classes.Triatomineos;
import com.example.squad2_suporte.enuns.StatusAmostra;
import com.example.squad2_suporte.repositorios.AmostraMoluscoRepository;
import com.example.squad2_suporte.repositorios.AmostraTriatomineosRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class VerificacaoHoras {

    @Autowired
    private AmostraMoluscoRepository amostraMoluscoRepository;

    @Autowired
    private AmostraTriatomineosRepository amostraTriatomineosRepository;

    @Transactional
    @Scheduled(fixedRate = 60000)
    public void verificarDataColetaMoluscos(){
        log.info("Executando verificação dataColeta Molusco em: {}", LocalDateTime.now());
        List<Molusco> moluscosPendente = amostraMoluscoRepository.findByStatus(StatusAmostra.PENDENTE);

        for (Molusco molusco : moluscosPendente){
            if (molusco.getDataHora().isBefore(LocalDateTime.now().minusHours(12))){
                molusco.setStatus(StatusAmostra.DESCARTADA);
                amostraMoluscoRepository.save(molusco);
                System.out.println("Molusco descartado por ultrapassar 12 horas: protocolo " + molusco.getProtocolo());
            }
        }
    }

    @Transactional
    @Scheduled(fixedRate = 60000)
    public void verificarDataColetaTriatomineos(){
        log.info("Executando verificação dataColeta Triatomineos em: {}", LocalDateTime.now());
        List<Triatomineos> triatomineosPendente = amostraTriatomineosRepository.findByStatus(StatusAmostra.PENDENTE);

        for (Triatomineos triato : triatomineosPendente){
            if (triato.getDataHora().isBefore(LocalDateTime.now().minusHours(48))){
                triato.setStatus(StatusAmostra.DESCARTADA);
                amostraTriatomineosRepository.save(triato);
                System.out.println("Molusco descartado por ultrapassar 12 horas: protocolo " + triato.getProtocolo());
            }
        }
    }
}
