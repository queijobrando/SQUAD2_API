package com.example.squad2_suporte.repositorios;

import com.example.squad2_suporte.Classes.Escorpioes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmostraEscorpiaoRepository extends JpaRepository<Escorpioes, Long> {
    Escorpioes findByProtocolo(String protocolo);
}
