package com.example.squad2_suporte.repositorios;

import com.example.squad2_suporte.Classes.Larvas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmostraLarvaRepository extends JpaRepository<Larvas, Long> {
    Larvas findByProtocolo(String protocolo);
}
