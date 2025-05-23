package com.example.squad2_suporte.service;

import com.example.squad2_suporte.enuns.model.Opcoes;
import com.example.squad2_suporte.repositorios.OpcoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OpcoesService {

    @Autowired
    private OpcoesRepository opcoesRepository;

    public Map<String, List<String>> listarPorTipo(String tipo) {
        List<Opcoes> opcoes = opcoesRepository.findByTipoIgnoreCase(tipo);

        return opcoes.stream()
                .collect(Collectors.groupingBy(
                        Opcoes::getChave,
                        Collectors.mapping(Opcoes::getValor, Collectors.toList())
                ));
    }
}
