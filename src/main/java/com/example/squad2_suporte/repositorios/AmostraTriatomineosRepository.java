package com.example.squad2_suporte.repositorios;

import com.example.squad2_suporte.Classes.Triatomineos;
import com.example.squad2_suporte.enuns.StatusAmostra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmostraTriatomineosRepository extends JpaRepository<Triatomineos, Long> {
    Triatomineos findByProtocolo(String protocolo);

    List<Triatomineos> findByStatus(StatusAmostra statusAmostra);
}
