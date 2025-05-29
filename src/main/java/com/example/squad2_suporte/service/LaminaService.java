package com.example.squad2_suporte.service;

import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.Amostras.mapper.LaminaMapper;
import com.example.squad2_suporte.Classes.*;
import com.example.squad2_suporte.Lamina.Lamina;
import com.example.squad2_suporte.config.exceptions.AmostraInvalidaException;
import com.example.squad2_suporte.config.exceptions.RecursoNaoEncontradoException;
import com.example.squad2_suporte.config.exceptions.RequisicaoInvalidaException;
import com.example.squad2_suporte.dto.lamina.LaminaDto;
import com.example.squad2_suporte.dto.lamina.RetornoLaminaDto;
import com.example.squad2_suporte.enuns.StatusAmostra;
import com.example.squad2_suporte.repositorios.LaminaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class LaminaService {

    @Autowired
    private LaminaRepository laminaRepository;

    @Autowired
    private LaminaMapper laminaMapper;

    @Transactional
    public Lamina cadastrarLamina(LaminaDto laminaDto){
        Lamina lamina = laminaMapper.dtoParaEntidade(laminaDto);
        return laminaRepository.save(lamina);
    }

    @Transactional
    public void deletarLamina(Long protocolo) {
        var lamina = laminaRepository.findByProtocolo(protocolo);
        if (lamina == null) {
            throw new RuntimeException("Protocolo inválido ou inexistente");
        }

        if (lamina.getLote() != null){
            throw new RequisicaoInvalidaException("A lamina com protocolo " + protocolo + " está associada a um lote e não pode ser deletada.");
        }

        lamina.setStatus(StatusAmostra.DESCARTADA);
        laminaRepository.save(lamina);

    }

    public Lamina buscarLamina(Long protocolo){
        var lamina = laminaRepository.findByProtocolo(protocolo);
        if (lamina == null){
            throw new RuntimeException("Protocolo inválido ou inexistente");
        } else {
            return lamina;
        }
    }

    public List<RetornoLaminaDto> listarLaminas(){
        return laminaRepository.findAll().stream().map(laminaMapper::entidadeParaRetorno).toList();
    }

    @Transactional
    public void adicionarLaudo(MultipartFile arquivo, Long protocolo) throws IOException {

        if (arquivo.isEmpty() || !Objects.requireNonNull(arquivo.getContentType()).equalsIgnoreCase("application/pdf")) {
            throw new IllegalArgumentException("Tipo de arquivo não suportado.");
        }

        Lamina lamina = laminaRepository.findByProtocolo(protocolo);
        if (lamina == null){
            throw new RecursoNaoEncontradoException("Protocolo inválido ou inexistente");
        }

        lamina.setLaudo(arquivo.getBytes());
        laminaRepository.save(lamina);
    }

    @Transactional
    public Lamina analisarLamina(Long protocolo){
        var lamina = laminaRepository.findByProtocolo(protocolo);
        if (lamina == null){
            throw new RecursoNaoEncontradoException("Protocolo inválido ou inexistente");
        }

        if (lamina.getStatus() == StatusAmostra.DESCARTADA){
            throw new AmostraInvalidaException("A lamina possui o status DESCARTADA e por tanto não pode ser mais ANALISADA");
        }

        lamina.setStatus(StatusAmostra.ANALISADA);
        laminaRepository.save(lamina);
        return lamina;
    }
}
