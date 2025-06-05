package com.example.squad2_suporte.repositorios;

import com.example.squad2_suporte.lote.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Long> {
    Lote findByProtocolo(String protocolo); // Alterado de Long para String
    @Query("SELECT DISTINCT l FROM Lote l JOIN l.amostras a WHERE a.endereco.municipio = :municipio")
    List<Lote> findByMunicipio(@Param("municipio") String municipio);
}