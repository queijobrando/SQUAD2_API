package com.example.squad2_suporte.repositorios;

import com.example.squad2_suporte.Amostras.Amostra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AmostraRepository extends JpaRepository<Amostra, Long> {
    Amostra findByProtocolo(Long protocolo);

    List<Amostra> findAllByProtocoloIn(List<Long> protocolos);
}
