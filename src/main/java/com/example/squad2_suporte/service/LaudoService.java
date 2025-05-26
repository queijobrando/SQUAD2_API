package com.example.squad2_suporte.service;

import com.example.squad2_suporte.Laudos.Laudo;
import com.example.squad2_suporte.repositorios.LaudoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class LaudoService {

    @Autowired
    private LaudoRepository laudoRepository;

    public Laudo salvar(MultipartFile arquivo) throws Exception {

        if (arquivo.isEmpty() || !arquivo.getContentType().equalsIgnoreCase("application/pdf")) {
            throw new IllegalArgumentException("Tipo de arquivo não suportado.");
        }

        Laudo laudo = new Laudo();
        laudo.setNome(arquivo.getOriginalFilename());
        laudo.setDados(arquivo.getBytes());
        return laudoRepository.save(laudo);

    }

    public Optional<Laudo> findById(Long id) {
        return laudoRepository.findById(id);
    }
}
