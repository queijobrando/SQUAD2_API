package com.example.squad2_suporte.service;

import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.Classes.Molusco;
import com.example.squad2_suporte.Classes.Triatomineos;
import com.example.squad2_suporte.Lamina.Lamina;
import com.example.squad2_suporte.enuns.StatusAmostra;
import com.example.squad2_suporte.repositorios.AmostraMoluscoRepository;
import com.example.squad2_suporte.repositorios.AmostraRepository;
import com.example.squad2_suporte.repositorios.AmostraTriatomineosRepository;
import com.example.squad2_suporte.repositorios.LaminaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private AmostraRepository amostraRepository;

    @Autowired
    private LaminaRepository laminaRepository;

    @Autowired
    private AuditService auditService;

    @Value("${lgpd.retention.days:90}")
    private int retentionDays;

    @Transactional
    @Scheduled(fixedRate = 60000)
    public void verificarDataColetaMoluscos() {
        log.info("Executando verificação dataColeta Molusco em: {}", LocalDateTime.now());
        List<Molusco> moluscosPendente = amostraMoluscoRepository.findByStatus(StatusAmostra.PENDENTE);
        for (Molusco molusco : moluscosPendente) {
            if (molusco.getDataHora().isBefore(LocalDateTime.now().minusHours(12))) {
                molusco.setStatus(StatusAmostra.DESCARTADA);
                amostraMoluscoRepository.save(molusco);
                log.info("Molusco descartado por ultrapassar 12 horas: protocolo {}", molusco.getProtocolo());
            }
        }
    }

    @Transactional
    @Scheduled(fixedRate = 60000)
    public void verificarDataColetaTriatomineos() {
        log.info("Executando verificação dataColeta Triatomineos em: {}", LocalDateTime.now());
        List<Triatomineos> triatomineosPendente = amostraTriatomineosRepository.findByStatus(StatusAmostra.PENDENTE);
        for (Triatomineos triato : triatomineosPendente) {
            if (triato.getDataHora().isBefore(LocalDateTime.now().minusHours(48))) {
                triato.setStatus(StatusAmostra.DESCARTADA);
                amostraTriatomineosRepository.save(triato);
                log.info("Triatomineos descartado por ultrapassar 48 horas: protocolo {}", triato.getProtocolo());
            }
        }
    }

    @Transactional
    @Scheduled(cron = "0 0 2 * * ?") // Executa diariamente às 2h
    public void verificarRetencaoDados() {
        log.info("Executando verificação de retenção LGPD em: {}", LocalDateTime.now());
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(retentionDays);

        // Anonimizar ou excluir amostras
        List<Amostra> amostrasExpiradas = amostraRepository.findByDataHoraBeforeAndStatusIn(
            cutoffDate, List.of(StatusAmostra.ANALISADA, StatusAmostra.DESCARTADA));
        for (Amostra amostra : amostrasExpiradas) {
            if (amostra.getLaudo() != null) {
                amostra.setLaudo(null);
                log.info("Laudo removido por política de retenção LGPD: protocolo {}", amostra.getProtocolo());
                auditService.logAction("LIMPEZA_DADOS", amostra.getProtocolo(), "Laudo removido por política de retenção LGPD");
            }
            if (amostra.getEndereco() != null) {
                amostra.getEndereco().setLogradouro(null);
                amostra.getEndereco().setNumero(null);
                amostra.getEndereco().setComplemento(null);
                amostra.getEndereco().setCep(null);
                log.info("Endereço anonimizado por política de retenção LGPD: protocolo {}", amostra.getProtocolo());
                auditService.logAction("LIMPEZA_DADOS", amostra.getProtocolo(), "Endereço anonimizado por política de retenção LGPD");
            }
            amostraRepository.delete(amostra);
            log.info("Amostra excluída por política de retenção LGPD: protocolo {}", amostra.getProtocolo());
            auditService.logAction("LIMPEZA_DADOS", amostra.getProtocolo(), "Amostra excluída por política de retenção LGPD");
        }

        // Excluir lâminas
        List<Lamina> laminasExpiradas = laminaRepository.findByDataBeforeAndStatusIn(
            cutoffDate, List.of(StatusAmostra.ANALISADA, StatusAmostra.DESCARTADA));
        for (Lamina lamina : laminasExpiradas) {
            laminaRepository.delete(lamina);
            log.info("Lâmina excluída por política de retenção LGPD: protocolo {}", lamina.getProtocolo());
            auditService.logAction("LIMPEZA_DADOS", lamina.getProtocolo(), "Lâmina excluída por política de retenção LGPD");
        }
    }
}