package com.example.squad2_suporte.service;

import com.example.squad2_suporte.Amostras.mapper.LaminaMapper;
import com.example.squad2_suporte.Lamina.Lamina;
import com.example.squad2_suporte.config.DataAnonymizer;
import com.example.squad2_suporte.config.exceptions.AmostraInvalidaException;
import com.example.squad2_suporte.config.exceptions.RecursoNaoEncontradoException;
import com.example.squad2_suporte.config.exceptions.RequisicaoInvalidaException;
import com.example.squad2_suporte.dto.lamina.EditarLaminaDto;
import com.example.squad2_suporte.dto.lamina.LaminaDto;
import com.example.squad2_suporte.dto.lamina.RetornoLaminaDto;
import com.example.squad2_suporte.enuns.StatusAmostra;
import com.example.squad2_suporte.enuns.StatusLote;
import com.example.squad2_suporte.repositorios.LaminaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LaminaService {

    @Autowired
    private LaminaRepository laminaRepository;

    @Autowired
    private LaminaMapper laminaMapper;

    @Autowired
    private ProtocoloSequenceService protocoloSequenceService;

    @Autowired
    private AuditService auditService;

    @Transactional
    public Lamina cadastrarLamina(LaminaDto laminaDto) {
        if (laminaDto.numeroOvos() != null && laminaDto.numeroOvos() < 0) {
            throw new IllegalArgumentException("O número de ovos não pode ser negativo");
        }
        Lamina lamina = laminaMapper.dtoParaEntidade(laminaDto);
        lamina.setData(LocalDateTime.now());
        lamina.setProtocolo(protocoloSequenceService.generateProtocolo("LAMINA"));
        lamina.setStatus(StatusAmostra.PENDENTE);
        if (laminaDto.enderecoCaptura() != null) {
            lamina.setEnderecoCaptura(DataAnonymizer.anonymizeAddressPartial(laminaDto.enderecoCaptura()));
        }
        lamina.setBaseLegal("Cumprimento de obrigação legal (Art. 7º, II, LGPD)");
        Lamina savedLamina = laminaRepository.save(lamina);
        auditService.logAction("CADASTRO_LAMINA", savedLamina.getProtocolo(), "Lâmina cadastrada");
        return savedLamina;
    }

    @Transactional
    public void deletarLamina(String protocolo) {
        Lamina lamina = laminaRepository.findByProtocolo(protocolo);
        if (lamina == null) {
            throw new RecursoNaoEncontradoException("Protocolo inválido ou inexistente");
        }
        if (lamina.getLote() != null) {
            throw new RequisicaoInvalidaException("A lâmina está associada a um lote e não pode ser deletada.");
        }
        lamina.setStatus(StatusAmostra.DESCARTADA);
        laminaRepository.save(lamina);
        auditService.logAction("DESCARTE_LAMINA", protocolo, "Lâmina descartada");
    }

    public Lamina buscarLamina(String protocolo) {
        Lamina lamina = laminaRepository.findByProtocolo(protocolo);
        if (lamina == null) {
            throw new RecursoNaoEncontradoException("Protocolo inválido ou inexistente");
        }
        auditService.logAction("CONSULTA_LAMINA", protocolo, "Lâmina consultada");
        return lamina;
    }

    public List<RetornoLaminaDto> listarLaminas() {
        List<Lamina> laminas = laminaRepository.findAll();
        auditService.logAction("LISTAGEM_LAMINAS", null, "Lista de lâminas consultada");
        return laminas.stream().map(laminaMapper::entidadeParaRetorno).toList();
    }

    @Transactional
    public void adicionarLaudo(MultipartFile arquivo, String protocolo) throws IOException {
        if (arquivo.isEmpty() || arquivo.getContentType() == null || !arquivo.getContentType().equalsIgnoreCase("application/pdf")) {
            throw new IllegalArgumentException("Tipo de arquivo não suportado.");
        }
        Lamina lamina = laminaRepository.findByProtocolo(protocolo);
        if (lamina == null) {
            throw new RecursoNaoEncontradoException("Protocolo inválido ou inexistente");
        }
        lamina.setLaudo(arquivo.getBytes());
        laminaRepository.save(lamina);
        auditService.logAction("ADICIONAR_LAUDO", protocolo, "Laudo adicionado à lâmina");
    }

    @Transactional
    public Lamina analisarLamina(String protocolo) {
        Lamina lamina = laminaRepository.findByProtocolo(protocolo);
        if (lamina == null) {
            throw new RecursoNaoEncontradoException("Protocolo inválido ou inexistente");
        }
        if (lamina.getStatus() == StatusAmostra.DESCARTADA) {
            throw new AmostraInvalidaException("A lâmina possui o status DESCARTADA e não pode ser mais ANALISADA");
        }
        lamina.setStatus(StatusAmostra.ANALISADA);
        laminaRepository.save(lamina);
        auditService.logAction("ANALISAR_LAMINA", protocolo, "Lâmina analisada");
        return lamina;
    }

    @Transactional
    public Lamina editarLamina(String protocolo, EditarLaminaDto dto) {
        Lamina lamina = laminaRepository.findByProtocolo(protocolo);
        if (lamina == null) {
            throw new RecursoNaoEncontradoException("Protocolo inválido ou inexistente");
        }
        if (lamina.getStatus() != StatusAmostra.PENDENTE) {
            throw new AmostraInvalidaException("A lâmina não está PENDENTE e não pode ser editada");
        }
        if (lamina.getLote() != null && lamina.getLote().getStatusLote() == StatusLote.ENVIADO) {
            throw new AmostraInvalidaException("A lâmina está associada a um lote ENVIADO e não pode ser editada");
        }
        if (dto.numeroOvos() != null) {
            if (dto.numeroOvos() < 0) {
                throw new IllegalArgumentException("O número de ovos não pode ser negativo");
            }
            lamina.setNumeroOvos(dto.numeroOvos());
        }
        if (dto.resultado() != null) {
            lamina.setResultado(dto.resultado());
        }
        Lamina updatedLamina = laminaRepository.save(lamina);
        auditService.logAction("EDITAR_LAMINA", protocolo, "Lâmina editada");
        return updatedLamina;
    }

    @Transactional
    public Lamina anonimizarLamina(String protocolo, String tipoAnonimizacao) {
        Lamina lamina = laminaRepository.findByProtocolo(protocolo);
        if (lamina == null) {
            throw new RecursoNaoEncontradoException("Protocolo inválido ou inexistente");
        }
        if (!tipoAnonimizacao.equalsIgnoreCase("PARCIAL") && !tipoAnonimizacao.equalsIgnoreCase("COMPLETA")) {
            throw new RequisicaoInvalidaException("Tipo de anonimização inválido. Use 'PARCIAL' ou 'COMPLETA'");
        }
        if (tipoAnonimizacao.equalsIgnoreCase("PARCIAL")) {
            lamina.setEnderecoCaptura(DataAnonymizer.anonymizeAddressPartial(lamina.getEnderecoCaptura()));
        } else {
            lamina.setEnderecoCaptura(DataAnonymizer.anonymizeAddressComplete(lamina.getEnderecoCaptura()));
        }
        lamina.setLaudo(null); // Remove laudo para LGPD
        Lamina savedLamina = laminaRepository.save(lamina);
        auditService.logAction("ANONIMIZACAO_LAMINA", protocolo, "Lâmina anonimizada: " + tipoAnonimizacao);
        return savedLamina;
    }
}