package com.example.squad2_suporte.repositorios;

import com.example.squad2_suporte.Lamina.Lamina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LaminaRepository extends JpaRepository<Lamina, Long> {
    Lamina findByProtocolo(Long protocolo);

    List<Lamina> findAllByProtocoloIn(List<Long> protocolos);
}
