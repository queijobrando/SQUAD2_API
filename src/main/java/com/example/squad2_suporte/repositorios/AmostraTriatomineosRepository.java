package com.example.squad2_suporte.repositorios;

import com.example.squad2_suporte.Classes.Triatomineos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmostraTriatomineosRepository extends JpaRepository<Triatomineos, Long> {
    Triatomineos findByProtocolo(Long protocolo);
}
