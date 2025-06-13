package com.example.squad2_suporte.service;

import com.example.squad2_suporte.Amostras.ProtocoloSequence;
import com.example.squad2_suporte.repositorios.ProtocoloSequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ProtocoloSequenceService {

    @Autowired
    private ProtocoloSequenceRepository protocoloSequenceRepository;

    @Transactional
    public String generateProtocolo(String tipo) {
        LocalDate today = LocalDate.now();
        String datePart = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        String sequenceKey = getSequenceKey(tipo);
        ProtocoloSequence sequence = protocoloSequenceRepository.findByDateAndSequenceKey(today, sequenceKey);
        if (sequence == null) {
            sequence = new ProtocoloSequence();
            sequence.setDate(today);
            sequence.setSequenceKey(sequenceKey);
            sequence.setSequence(1L);
            protocoloSequenceRepository.save(sequence);
        } else {
            protocoloSequenceRepository.incrementSequence(today, sequenceKey);
            sequence.setSequence(sequence.getSequence() + 1);
        }

        String sequencePart = String.format("%05d", sequence.getSequence());
        String prefix = getPrefix(tipo);
        return prefix + datePart + "-" + sequencePart;
    }

    private String getSequenceKey(String tipo) {
        return switch (tipo.toUpperCase()) {
            case "LOTE" -> "LOTE";
            case "AMOSTRA", "LAMINA" -> "AMOSTRA_LAMINA";
            default -> throw new IllegalArgumentException("Tipo inválido: " + tipo);
        };
    }

    private String getPrefix(String tipo) {
        return switch (tipo.toUpperCase()) {
            case "AMOSTRA" -> "AM-";
            case "LAMINA" -> "LM-";
            default -> ""; // Para lotes, sem prefixo
        };
    }
}