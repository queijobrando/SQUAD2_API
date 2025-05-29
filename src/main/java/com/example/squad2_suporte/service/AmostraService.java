package com.example.squad2_suporte.service;

import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.Amostras.mapper.*;
import com.example.squad2_suporte.Classes.*;
import com.example.squad2_suporte.Lamina.Lamina;
import com.example.squad2_suporte.config.exceptions.AmostraInvalidaException;
import com.example.squad2_suporte.config.exceptions.RecursoNaoEncontradoException;
import com.example.squad2_suporte.config.exceptions.RequisicaoInvalidaException;
import com.example.squad2_suporte.dto.amostra.AmostraDto;
import com.example.squad2_suporte.dto.amostra.ProtocoloAmostraDto;
import com.example.squad2_suporte.dto.amostra.ProtocoloListaAmostraDto;
import com.example.squad2_suporte.enuns.StatusAmostra;
import com.example.squad2_suporte.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class AmostraService {

    @Autowired
    private AmostraRepository amostraRepository;

    @Autowired
    private AmostraEscorpiaoRepository amostraEscorpiaoRepository;

    @Autowired
    private AmostraFlebotomineosRepository amostraFlebotomineosRepository;

    @Autowired
    private AmostraTriatomineosRepository amostraTriatomineosRepository;

    @Autowired
    private AmostraMoluscoRepository amostraMoluscoRepository;

    @Autowired
    private AmostraLarvaRepository amostraLarvaRepository;

    @Autowired
    private TipoAmostraMapper tipoAmostraMapper;

    @Transactional
    public Object cadastrarAmostraUnificada(AmostraDto dto) {
        switch (dto.tipoAmostra()) {
            case ESCORPIAO -> {
                Escorpioes escorpiao = tipoAmostraMapper.amostraDtoParaEscorpiao(dto);

                // Verificação se está esmagado
                if (escorpiao.getSofreuAcidente()){
                    throw new AmostraInvalidaException("Amostras do tipo ESCORPIÃO não podem estar esmagadas.");
                }

                amostraEscorpiaoRepository.save(escorpiao);
                return tipoAmostraMapper.escorpiaoEntidadeParaRetorno(escorpiao);
            }
            case FLEBOTOMINEOS -> {
                Flebotomineos flebo = tipoAmostraMapper.amostraDtoParaFlebotomineos(dto);
                amostraFlebotomineosRepository.save(flebo);
                return tipoAmostraMapper.flebotomineosEntidadeParaRetorno(flebo);
            }
            case TRIATOMINEOS -> {
                Triatomineos triato = tipoAmostraMapper.amostraDtoParaTriatomineos(dto);

                // Verificação da diferença entre dataHora e a atual
                if (triato.getDataHora().isBefore(LocalDateTime.now().minusHours(48))) {
                    throw new AmostraInvalidaException("Amostras do tipo TRIATOMINEOS não podem ter mais de 48 horas desde a coleta.");
                }

                amostraTriatomineosRepository.save(triato);
                return tipoAmostraMapper.triatomieosEntidadeParaRetorno(triato);
            }
            case MOLUSCO -> {
                Molusco molusco = tipoAmostraMapper.amostraDtoParaMolusco(dto);

                // Verificação da diferença entre dataHora e a atual
                if (molusco.getDataHora().isBefore(LocalDateTime.now().minusHours(12))) {
                    throw new AmostraInvalidaException("Amostras do tipo MOLUSCO não podem ter mais de 12 horas desde a coleta.");
                }

                amostraMoluscoRepository.save(molusco);
                return tipoAmostraMapper.moluscoEntidadeParaRetorno(molusco);
            }
            case LARVAS -> {
                Larvas larvas = tipoAmostraMapper.amostraDtoParaLarva(dto);
                amostraLarvaRepository.save(larvas);
                return tipoAmostraMapper.larvasEntidadeParaRetorno(larvas);
            }
            default -> throw new RequisicaoInvalidaException("Tipo de amostra inválido: " + dto.tipoAmostra());
        }
    }

    public void deletarAmostra(Long protocolo) {
        var amostra = amostraRepository.findByProtocolo(protocolo);
        if (amostra == null){
            throw new RecursoNaoEncontradoException("Protocolo inválido ou inexistente");
        }

        if (amostra.getLote() != null){
            throw new RequisicaoInvalidaException("A amostra com protocolo " + protocolo + " está associada a um lote e não pode ser deletada.");
        }

        amostra.setStatus(StatusAmostra.DESCARTADA);
        amostraRepository.save(amostra);
    }

    public Amostra retornarAmostra(Long protocolo){
        var amostra = amostraRepository.findByProtocolo(protocolo);
        if (amostra == null){
            throw new RecursoNaoEncontradoException("Protocolo inválido ou inexistente");
        }

        return amostra;
    }

    public Object buscarAmostra(Long protocolo){
        var amostra = amostraRepository.findByProtocolo(protocolo);
        if (amostra == null){
            throw new RecursoNaoEncontradoException("Protocolo inválido ou inexistente");
        } else {
            switch (amostra.getTipoAmostra()){
                case ESCORPIAO -> {
                    Escorpioes escorpioes = amostraEscorpiaoRepository.findByProtocolo(protocolo);
                    return tipoAmostraMapper.escorpiaoEntidadeParaRetorno(escorpioes);
                }
                case FLEBOTOMINEOS -> {
                    Flebotomineos flebo = amostraFlebotomineosRepository.findByProtocolo(protocolo);
                    return tipoAmostraMapper.flebotomineosEntidadeParaRetorno(flebo);
                }
                case TRIATOMINEOS -> {
                    Triatomineos triato = amostraTriatomineosRepository.findByProtocolo(protocolo);
                    return tipoAmostraMapper.triatomieosEntidadeParaRetorno(triato);
                }
                case LARVAS -> {
                    Larvas larvas = amostraLarvaRepository.findByProtocolo(protocolo);
                    return tipoAmostraMapper.larvasEntidadeParaRetorno(larvas);
                }
                case MOLUSCO -> {
                    Molusco molusco = amostraMoluscoRepository.findByProtocolo(protocolo);
                    return tipoAmostraMapper.moluscoEntidadeParaRetorno(molusco);
                }
                default -> throw new RecursoNaoEncontradoException("Tipo de amostra não encontrada");
            }
        }

    }

    public List<ProtocoloListaAmostraDto> listarTodasAmostras(){
        return amostraRepository.findAll().stream().map(tipoAmostraMapper::listagemAmostras).toList();
    }

    @Transactional
    public void adicionarLaudo(MultipartFile arquivo, Long protocolo) throws IOException {

        if (arquivo.isEmpty() || !Objects.requireNonNull(arquivo.getContentType()).equalsIgnoreCase("application/pdf")) {
            throw new IllegalArgumentException("Tipo de arquivo não suportado.");
        }

        Amostra amostra = amostraRepository.findByProtocolo(protocolo);
        if (amostra == null){
            throw new RecursoNaoEncontradoException("Protocolo inválido ou inexistente");
        }

        amostra.setLaudo(arquivo.getBytes());
        amostraRepository.save(amostra);
    }

    @Transactional
    public Object analisarAmostra(Long protocolo){
        var amostra = amostraRepository.findByProtocolo(protocolo);
        if (amostra == null){
            throw new RecursoNaoEncontradoException("Protocolo inválido ou inexistente");
        }

        if (amostra.getStatus() == StatusAmostra.DESCARTADA){
            throw new AmostraInvalidaException("A amostra possui o status DESCARTADA e por tanto não pode ser mais ANALISADA");
        } else {
            amostra.setStatus(StatusAmostra.ANALISADA);
            amostraRepository.save(amostra);
            switch (amostra.getTipoAmostra()){
                case ESCORPIAO -> {
                    Escorpioes escorpioes = amostraEscorpiaoRepository.findByProtocolo(protocolo);
                    return tipoAmostraMapper.escorpiaoEntidadeParaRetorno(escorpioes);
                }
                case FLEBOTOMINEOS -> {
                    Flebotomineos flebo = amostraFlebotomineosRepository.findByProtocolo(protocolo);
                    return tipoAmostraMapper.flebotomineosEntidadeParaRetorno(flebo);
                }
                case TRIATOMINEOS -> {
                    Triatomineos triato = amostraTriatomineosRepository.findByProtocolo(protocolo);
                    return tipoAmostraMapper.triatomieosEntidadeParaRetorno(triato);
                }
                case LARVAS -> {
                    Larvas larvas = amostraLarvaRepository.findByProtocolo(protocolo);
                    return tipoAmostraMapper.larvasEntidadeParaRetorno(larvas);
                }
                case MOLUSCO -> {
                    Molusco molusco = amostraMoluscoRepository.findByProtocolo(protocolo);
                    return tipoAmostraMapper.moluscoEntidadeParaRetorno(molusco);
                }
                default -> throw new RecursoNaoEncontradoException("Tipo de amostra não encontrada");
            }
        }
    }


}
