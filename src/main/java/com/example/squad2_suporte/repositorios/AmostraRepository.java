package com.example.squad2_suporte.repositorios;

import com.example.squad2_suporte.Amostras.Amostra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmostraRepository extends JpaRepository<Amostra, Long> {
    Amostra findByProtocolo(Long protocolo);
}
