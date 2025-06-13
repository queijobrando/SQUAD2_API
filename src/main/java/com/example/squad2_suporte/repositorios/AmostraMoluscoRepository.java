package com.example.squad2_suporte.repositorios;

import com.example.squad2_suporte.Classes.Molusco;
import com.example.squad2_suporte.enuns.StatusAmostra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmostraMoluscoRepository extends JpaRepository<Molusco, Long> {
    Molusco findByProtocolo(String protocolo);

    List<Molusco> findByStatus(StatusAmostra statusAmostra);
}
