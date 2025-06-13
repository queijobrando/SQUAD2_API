package com.example.squad2_suporte.config;

import com.example.squad2_suporte.service.AmostraVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private AmostraVerificationService amostraVerificationService;

    @Scheduled(fixedRate = 3600000) // Executa a cada hora (3600000 ms)
    public void verificarAmostrasVencidas() {
        amostraVerificationService.verificarEAplicarDescartes();
    }
}