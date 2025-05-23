package com.example.squad2_suporte.repositorios;

import com.example.squad2_suporte.enuns.model.Opcoes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OpcoesRepository extends JpaRepository<Opcoes, Long> {
    List<Opcoes> findByTipoIgnoreCase(String tipo);
}
