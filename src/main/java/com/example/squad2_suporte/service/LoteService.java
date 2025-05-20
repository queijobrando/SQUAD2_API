package com.example.squad2_suporte.service;

import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.Amostras.mapper.LoteMapper;
import com.example.squad2_suporte.dto.lote.LoteDto;
import com.example.squad2_suporte.dto.lote.RetornoLoteDto;
import com.example.squad2_suporte.lote.Lote;
import com.example.squad2_suporte.repositorios.AmostraRepository;
import com.example.squad2_suporte.repositorios.LoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoteService {

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private AmostraRepository amostraRepository;

    @Autowired
    private LoteMapper loteMapper;

    public Lote cadastrarLote(LoteDto loteDto){
        if (loteDto.protocoloAmostras() == null || loteDto.protocoloAmostras().isEmpty()){
            throw new RuntimeException("É necessário ao menos uma amostra para criar um lote!");
        }

        // Cria uma lista de amostras com base no id fornecido no dto
        List<Amostra> listaAmostras = amostraRepository.findAllByProtocoloIn(loteDto.protocoloAmostras());

        if (listaAmostras.size() != loteDto.protocoloAmostras().size()){
            throw new RuntimeException("Alguma amostra não foi encontrada");
        }

        // Verifica se a alguma amostra ja tem lote
        for (Amostra amostra : listaAmostras){
            if (amostra.getLote() != null){
                throw new RuntimeException("A amostra com protocolo " + amostra.getProtocolo() + " já possui um lote cadastrado!");
            }
        }

        //Seta o lote ID em cada amostra
        Lote lote = new Lote();
        for (Amostra amostra : listaAmostras) {
            amostra.setLote(lote);
            lote.getAmostras().add(amostra);
        }

        return loteRepository.save(lote);
    }

    public List<RetornoLoteDto> listarLotes() {
        return loteRepository.findAll().stream()
                .map(loteMapper::entidadeParaRetorno)
                .toList();
    }

}
