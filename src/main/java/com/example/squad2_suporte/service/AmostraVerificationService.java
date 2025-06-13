package com.example.squad2_suporte.service;

import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.enuns.StatusAmostra;
import com.example.squad2_suporte.enuns.TipoAmostra;
import com.example.squad2_suporte.repositorios.AmostraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AmostraVerificationService {

    @Autowired
    private AmostraRepository amostraRepository;

    @Autowired
    private AuditService auditService;

    @Transactional
    public void verificarEAplicarDescartes() {
        List<Amostra> amostrasPendentes = amostraRepository.findByStatus(StatusAmostra.PENDENTE);
        for (Amostra amostra : amostrasPendentes) {
            if (isVencida(amostra)) {
                amostra.setStatus(StatusAmostra.DESCARTADA);
                amostraRepository.save(amostra);
                auditService.logAction("DESCARTE_AUTOMATICO", amostra.getProtocolo(), "Amostra descartada por vencimento");
            }
        }
    }

    private boolean isVencida(Amostra amostra) {
        LocalDateTime now = LocalDateTime.now();
        switch (amostra.getTipoAmostra()) {
            case MOLUSCO:
                return amostra.getDataHora().isBefore(now.minusHours(12));
            case TRIATOMINEOS:
                return amostra.getDataHora().isBefore(now.minusHours(48));
            default:
                return false;
        }
    }
}