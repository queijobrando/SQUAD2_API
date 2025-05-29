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
import com.example.squad2_suporte.enuns.StatusAmostra;
import com.example.squad2_suporte.enuns.StatusLote;
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

        int totalEncontrado = amostras.size() + laminas.size();

        if (totalEncontrado != loteDto.protocolos().size()){
            throw new RequisicaoInvalidaException("Alguma amostra não foi encontrada");
        }

        // Regra: não pode haver mistura de tipos
        if (!amostras.isEmpty() && !laminas.isEmpty()) {
            throw new RequisicaoInvalidaException("Não é permitido criar um lote com amostras e lâminas ao mesmo tempo.");
        }

        Lote lote = new Lote();

        if (!amostras.isEmpty()) {
            for (Amostra amostra : amostras) {
                if (amostra.getLote() != null) {
                    throw new AmostraInvalidaException("A amostra com protocolo " + amostra.getProtocolo() + " já possui um lote cadastrado!");
                }
                amostra.setLote(lote);
                lote.getAmostras().add(amostra);
            }
        } else {
            for (Lamina lamina : laminas) {
                if (lamina.getLote() != null) {
                    throw new AmostraInvalidaException("A lâmina com protocolo " + lamina.getProtocolo() + " já possui um lote cadastrado!");
                }
                lamina.setLote(lote);
                lote.getLaminas().add(lamina);
            }
        }

        return loteRepository.save(lote);
    }

    public List<RetornoLoteDto> listarLotes() {
        return loteRepository.findAll().stream()
                .map(loteMapper::entidadeParaRetorno)
                .toList();
    }

    @Transactional
    public RetornoLoteDto editarLote(EditarLoteDto dto, Long loteProtocolo) {
        if (dto.protocolos() == null || dto.protocolos().isEmpty()) {
            throw new LoteInvalidoException("É necessário ao menos uma amostra ou lâmina para editar um lote!");
        }

        List<Amostra> listaAmostras = amostraRepository.findAllByProtocoloIn(dto.protocolos());
        List<Lamina> listaLaminas = laminaRepository.findAllByProtocoloIn(dto.protocolos());

        int totalEncontrado = listaAmostras.size() + listaLaminas.size();

        if (totalEncontrado != dto.protocolos().size()) {
            throw new RequisicaoInvalidaException("Alguma amostra ou lâmina não foi encontrada");
        }

        if (!listaAmostras.isEmpty() && !listaLaminas.isEmpty()) {
            throw new RequisicaoInvalidaException("Não é permitido editar um lote com amostras e lâminas ao mesmo tempo.");
        }

        Lote lote = loteRepository.findByProtocolo(loteProtocolo);
        if (lote == null) {
            throw new RequisicaoInvalidaException("Lote com protocolo " + loteProtocolo + " não encontrado");
        }

        if (lote.getStatusLote() == StatusLote.DESCARTADO){
            throw new LoteInvalidoException("O lote inserido já foi DESCARTADO e por isso não pode ser editado.");
        }

        boolean loteContemAmostras = !lote.getAmostras().isEmpty();
        boolean loteContemLaminas = !lote.getLaminas().isEmpty();

        // Verificação de tipo único no lote
        if ((loteContemAmostras && !listaAmostras.isEmpty()) || (loteContemLaminas && !listaLaminas.isEmpty())) {
            // OK - tipos batem
        } else if ((loteContemAmostras && !listaLaminas.isEmpty()) || (loteContemLaminas && !listaAmostras.isEmpty())) {
            throw new RequisicaoInvalidaException("Não é permitido adicionar ou remover itens de tipo diferente dos já presentes no lote.");
        } else if (!loteContemAmostras && !loteContemLaminas) {
            // Lote está vazio, qualquer tipo é aceito
        }

        switch (dto.opcao()) {
            case ADICIONAR -> {
                for (Amostra amostra : listaAmostras) {
                    if (amostra.getLote() != null) {
                        throw new AmostraInvalidaException("A amostra com protocolo " + amostra.getProtocolo() + " já possui um lote cadastrado!");
                    }
                    amostra.setLote(lote);
                    lote.getAmostras().add(amostra);
                }

                for (Lamina lamina : listaLaminas) {
                    if (lamina.getLote() != null) {
                        throw new AmostraInvalidaException("A lâmina com protocolo " + lamina.getProtocolo() + " já possui um lote cadastrado!");
                    }
                    lamina.setLote(lote);
                    lote.getLaminas().add(lamina);
                }
            }

            case REMOVER -> {
                for (Amostra amostra : listaAmostras) {
                    if (!lote.equals(amostra.getLote())) {
                        throw new AmostraInvalidaException("A amostra com protocolo " + amostra.getProtocolo() + " não pertence a este lote!");
                    }
                    amostra.setLote(null);
                    lote.getAmostras().remove(amostra);
                }

                for (Lamina lamina : listaLaminas) {
                    if (!lote.equals(lamina.getLote())) {
                        throw new AmostraInvalidaException("A lâmina com protocolo " + lamina.getProtocolo() + " não pertence a este lote!");
                    }
                    lamina.setLote(null);
                    lote.getLaminas().remove(lamina);
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

    @Transactional
    public RetornoLoteDto enviarLote(Long protocolo){
        Lote lote = loteRepository.findByProtocolo(protocolo);
        if (lote == null){
            throw new LoteInvalidoException("Protocolo inválido ou inexistente");
        }

        if (lote.getStatusLote() == StatusLote.DESCARTADO){
            throw new LoteInvalidoException("O lote inserido já foi DESCARTADO e por isso não pode ser enviado.");
        } else if (lote.getStatusLote() == StatusLote.ENVIADO) {
            throw new LoteInvalidoException("O lote inserido já foi enviado para processamento.");
        }

        List<Amostra> amostras = lote.getAmostras();
        List<Lamina> laminas = lote.getLaminas();

        for (Amostra amostra : amostras){
            if (amostra.getStatus() == StatusAmostra.DESCARTADA){
                throw new AmostraInvalidaException("A amostra de protocolo " + amostra.getProtocolo() + " foi descartada e não pode ser enviada.");
            } else if (amostra.getStatus() == StatusAmostra.PENDENTE) {
                throw new AmostraInvalidaException("A amostra de protocolo " + amostra.getProtocolo() + " está PENDENTE e precisa ser ANALISADA antes do envio");
            }
        }

        for (Lamina lamina : laminas){
            if (lamina.getStatus() == StatusAmostra.DESCARTADA){
                throw new AmostraInvalidaException("A lamina de protocolo " + lamina.getProtocolo() + " foi descartada e não pode ser enviada.");
            } else if (lamina.getStatus() == StatusAmostra.PENDENTE) {
                throw new AmostraInvalidaException("A lamina de protocolo " + lamina.getProtocolo() + " está PENDENTE e precisa ser ANALISADA antes do envio");
            }
        }

        lote.setStatusLote(StatusLote.ENVIADO);
        loteRepository.save(lote);

        return loteMapper.entidadeParaRetorno(lote);

    }

    @Transactional
    public RetornoLoteDto descartarLote(Long protocolo){
        Lote lote = loteRepository.findByProtocolo(protocolo);
        if (lote == null){
            throw new LoteInvalidoException("Protocolo inválido ou inexistente");
        }

        if (lote.getStatusLote() == StatusLote.ENVIADO) {
            throw new LoteInvalidoException("O lote inserido já foi enviado para processamento, e por tanto, não pode ser descartado.");
        }

        lote.setStatusLote(StatusLote.DESCARTADO);
        loteRepository.save(lote);

        return loteMapper.entidadeParaRetorno(lote);
    }


}
