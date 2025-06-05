package com.example.squad2_suporte.repositorios;

import com.example.squad2_suporte.Classes.Flebotomineos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmostraFlebotomineosRepository extends JpaRepository<Flebotomineos, Long> {
    Flebotomineos findByProtocolo(String protocolo);
}
