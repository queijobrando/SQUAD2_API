package com.example.squad2_suporte.service;

import com.example.squad2_suporte.Amostras.mapper.LaminaMapper;
import com.example.squad2_suporte.Lamina.Lamina;
import com.example.squad2_suporte.dto.lamina.LaminaDto;
import com.example.squad2_suporte.dto.lamina.RetornoLaminaDto;
import com.example.squad2_suporte.repositorios.LaminaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
