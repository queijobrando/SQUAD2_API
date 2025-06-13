package com.example.squad2_suporte.config;

import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.enuns.StatusAmostra;
import com.example.squad2_suporte.enuns.TipoAmostra;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class AmostraSpecification {

    public static Specification<Amostra> filtrarAmostras(TipoAmostra tipoAmostra, StatusAmostra status, String municipio) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por tipoAmostra, se informado
            if (tipoAmostra != null) {
                predicates.add(criteriaBuilder.equal(root.get("tipoAmostra"), tipoAmostra));
            }

            // Filtro por status, se informado
            if (status != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            // Filtro por município, se informado
            if (municipio != null && !municipio.isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("endereco").get("municipio"), municipio));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}