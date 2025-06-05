package com.example.squad2_suporte.repositorios;

import com.example.squad2_suporte.Amostras.ProtocoloSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

public interface ProtocoloSequenceRepository extends JpaRepository<ProtocoloSequence, Long> {
    ProtocoloSequence findByDateAndSequenceKey(LocalDate date, String sequenceKey);

    @Transactional
    @Modifying
    @Query("UPDATE ProtocoloSequence ps SET ps.sequence = ps.sequence + 1 WHERE ps.date = :date AND ps.sequenceKey = :sequenceKey")
    void incrementSequence(LocalDate date, String sequenceKey);
}