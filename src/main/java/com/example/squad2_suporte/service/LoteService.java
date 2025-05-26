package com.example.squad2_suporte.service;

import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.Amostras.mapper.LoteMapper;
import com.example.squad2_suporte.Lamina.Lamina;
import com.example.squad2_suporte.config.exceptions.AmostraInvalidaException;
import com.example.squad2_suporte.config.exceptions.LoteInvalidoException;
import com.example.squad2_suporte.config.exceptions.RequisicaoInvalidaException;
import com.example.squad2_suporte.dto.lote.EditarLoteDto;
import com.example.squad2_suporte.dto.lote.LoteDto;
import com.example.squad2_suporte.dto.lote.RetornoLoteDto;
import com.example.squad2_suporte.lote.Lote;
import com.example.squad2_suporte.repositorios.AmostraRepository;
import com.example.squad2_suporte.repositorios.LaminaRepository;
import com.example.squad2_suporte.repositorios.LoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoteService {

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private AmostraRepository amostraRepository;

    @Autowired
    private LaminaRepository laminaRepository;

    @Autowired
    private LoteMapper loteMapper;

    @Transactional
    public Lote cadastrarLote(LoteDto loteDto){
        if (loteDto.protocolos() == null || loteDto.protocolos().isEmpty()){
            throw new LoteInvalidoException("É necessário ao menos uma amostra ou lamina para criar um lote!");
        }

        // Cria uma lista de amostras com base no id fornecido no dto
        List<Amostra> amostras = amostraRepository.findAllByProtocoloIn(loteDto.protocolos());
        List<Lamina> laminas = laminaRepository.findAllByProtocoloIn(loteDto.protocolos());

        List<Object> coletas = new ArrayList<>();
        coletas.addAll(amostras);
        coletas.addAll(laminas);

        if (coletas.size() != loteDto.protocolos().size()){
            throw new RequisicaoInvalidaException("Alguma amostra não foi encontrada");
        }

        // Verifica se a alguma amostra ja tem lote
        for (Amostra amostra : amostras){
            if (amostra.getLote() != null){
                throw new AmostraInvalidaException("A amostra com protocolo " + amostra.getProtocolo() + " já possui um lote cadastrado!");
            }
        }
        // Verifica se a alguma lamina ja tem lote
        for (Lamina lamina : laminas){
            if (lamina.getLote() != null){
                throw new AmostraInvalidaException("A amostra com protocolo " + lamina.getProtocolo() + " já possui um lote cadastrado!");
            }
        }


        //Seta o lote ID em cada amostra
        Lote lote = new Lote();
        for (Amostra amostra : amostras) {
            amostra.setLote(lote);
            lote.getAmostras().add(amostra);
        }
        for (Lamina lamina : laminas) {
            lamina.setLote(lote);
            lote.getLaminas().add(lamina);
        }

        return loteRepository.save(lote);
    }

    public List<RetornoLoteDto> listarLotes() {
        return loteRepository.findAll().stream()
                .map(loteMapper::entidadeParaRetorno)
                .toList();
    }

    @Transactional
    public RetornoLoteDto editarLote(EditarLoteDto dto, Long loteProtocolo){
        if (dto.protocoloAmostras() == null || dto.protocoloAmostras().isEmpty()){
            throw new LoteInvalidoException("É necessário ao menos uma amostra para editar um lote!");
        }

        List<Amostra> listaAmostras = amostraRepository.findAllByProtocoloIn(dto.protocoloAmostras());

        if (listaAmostras.size() != dto.protocoloAmostras().size()){
            throw new RequisicaoInvalidaException("Alguma amostra não foi encontrada");
        }

        Lote lote = loteRepository.findByProtocolo(loteProtocolo);
        if (lote == null) {
            throw new RequisicaoInvalidaException("Lote com protocolo " + loteProtocolo + " não encontrado");
        }

        switch (dto.opcao()){
            case ADICIONAR -> {
                // Verifica se a alguma amostra ja tem lote
                for (Amostra amostra : listaAmostras){
                    if (amostra.getLote() != null){
                        throw new AmostraInvalidaException("A amostra com protocolo " + amostra.getProtocolo() + " já possui um lote cadastrado!");
                    }
                    amostra.setLote(lote);
                    lote.getAmostras().add(amostra);
                }
            }
            case REMOVER -> {
                // Verifica se a alguma amostra ja tem lote
                for (Amostra amostra : listaAmostras){
                    if (!lote.equals((amostra.getLote()))){
                        throw new AmostraInvalidaException("A amostra com protocolo " + amostra.getProtocolo() + "não pertence a este lote!");
                    }
                    amostra.setLote(null);
                    lote.getAmostras().remove(amostra);
                }

            }
            default -> throw new RequisicaoInvalidaException("Opção Inválida: " + dto.opcao());
        }

        return loteMapper.entidadeParaRetorno(lote);
    }
    public List<RetornoLoteDto> buscarLotesPorMunicipio(String municipio) {
        List<Lote> lotes = loteRepository.findByMunicipio(municipio);
        return lotes.stream()
                .map(loteMapper::entidadeParaRetorno)
                .toList();
    }


}
