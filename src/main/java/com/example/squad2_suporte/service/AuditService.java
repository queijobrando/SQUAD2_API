package com.example.squad2_suporte.service;

import com.example.squad2_suporte.Usuario.AuditLog;
import com.example.squad2_suporte.repositorios.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import com.example.squad2_suporte.lote.Lote;

@Service
public class AuditService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Transactional
    public void logAction(String action, String resourceId, String details) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AuditLog log = new AuditLog();
        log.setUsername(username);
        log.setAction(action);
        log.setResourceId(resourceId);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }

    @Transactional
    public void logAlteracaoLote(String user, Lote lote, String acao) {
        AuditLog log = new AuditLog();
        log.setUsername(user);
        log.setAction(acao);
        log.setResourceType("LOTE");
        log.setResourceId(lote.getProtocolo());
        log.setDetails("Ação " + acao + " no lote " + lote.getProtocolo());
        log.setTimestamp(LocalDateTime.now());
        auditLogRepository.save(log);
    }
}