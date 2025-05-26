package com.example.squad2_suporte.service;

import com.example.squad2_suporte.Amostras.mapper.LaminaMapper;
import com.example.squad2_suporte.Lamina.Lamina;
import com.example.squad2_suporte.config.exceptions.RequisicaoInvalidaException;
import com.example.squad2_suporte.dto.lamina.LaminaDto;
import com.example.squad2_suporte.dto.lamina.RetornoLaminaDto;
import com.example.squad2_suporte.repositorios.LaminaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

            laminaRepository.delete(lamina);

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
}
