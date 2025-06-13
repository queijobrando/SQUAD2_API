package com.example.squad2_suporte.repositorios;

import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.enuns.StatusAmostra;
import com.example.squad2_suporte.enuns.TipoAmostra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AmostraRepository extends JpaRepository<Amostra, Long> {
    Amostra findByProtocolo(String protocolo);

    List<Amostra> findAllByProtocoloIn(List<String> protocolos);

    List<Amostra> findByTipoAmostra(TipoAmostra tipoAmostra);

    List<Amostra> findByStatus(StatusAmostra status);

    List<Amostra> findByTipoAmostraAndStatus(TipoAmostra tipoAmostra, StatusAmostra status);

    List<Amostra> findByDataHoraBeforeAndStatusIn(LocalDateTime cutoffDate, List<StatusAmostra> statuses);

    // Novo método para suportar filtros dinâmicos e paginação
    Page<Amostra> findAll(Specification<Amostra> spec, Pageable pageable);
}