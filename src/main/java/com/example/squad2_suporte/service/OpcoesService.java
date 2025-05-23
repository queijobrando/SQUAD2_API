package com.example.squad2_suporte.service;

import com.example.squad2_suporte.enuns.model.Opcoes;
import com.example.squad2_suporte.repositorios.OpcoesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpcoesService {

    @Autowired
    private OpcoesRepository opcoesRepository;

    public List<String> listarOpcoes(String tipo, String chave){
        List<Opcoes> opcoes = opcoesRepository.findByTipoAndChave(tipo.toUpperCase(), chave.toUpperCase());

        return opcoes.stream().map(Opcoes::getValor).toList();
    }
}
