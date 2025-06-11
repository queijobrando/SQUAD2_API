package com.example.squad2_suporte.service;

import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.Amostras.mapper.EnderecoMapper;
import com.example.squad2_suporte.Amostras.mapper.TipoAmostraMapper;
import com.example.squad2_suporte.Classes.*;
import com.example.squad2_suporte.config.exceptions.AmostraInvalidaException;
import com.example.squad2_suporte.config.exceptions.RecursoNaoEncontradoException;
import com.example.squad2_suporte.config.exceptions.RequisicaoInvalidaException;
import com.example.squad2_suporte.dto.amostra.AmostraDto;
import com.example.squad2_suporte.dto.amostra.EditarAmostraDto;
import com.example.squad2_suporte.dto.amostra.ProtocoloListaAmostraDto;
import com.example.squad2_suporte.enuns.StatusAmostra;
import com.example.squad2_suporte.enuns.StatusLote;
import com.example.squad2_suporte.enuns.TipoAmostra;
import com.example.squad2_suporte.repositorios.*;
import com.example.squad2_suporte.config.DataAnonymizer;
import com.example.squad2_suporte.config.AmostraSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    private EnderecoMapper enderecoMapper;

    @Autowired
    private ProtocoloSequenceService protocoloSequenceService;

    @Autowired
    private AuditService auditService;

    @Transactional
    public Object cadastrarAmostraUnificada(AmostraDto dto) {
        // Validações condicionais por tipo de amostra
        switch (dto.tipoAmostra()) {
            case ESCORPIAO:
                if (dto.quantidade() == null || dto.quantidade() <= 0) {
                    throw new IllegalArgumentException("O campo quantidade é obrigatório e deve ser maior que zero para ESCORPIAO");
                }
                if (dto.sofreuAcidente() == null) {
                    throw new IllegalArgumentException("O campo sofreuAcidente é obrigatório para ESCORPIAO");
                }
                break;
            case FLEBOTOMINEOS:
                if (dto.classificacaoAreaLV() == null || dto.classificacaoAreaLT() == null ||
                    dto.tipoAtividadeLV() == null || dto.tipoAtividadeLT() == null ||
                    dto.tipoVegetacao() == null || dto.distanciaVegetacao() == null ||
                    dto.temperaturaChegada() == null || dto.temperaturaSaida() == null ||
                    dto.temperaturaMax() == null || dto.temperaturaMin() == null ||
                    dto.umidadeChegada() == null || dto.umidadeSaida() == null ||
                    dto.umidadeMax() == null || dto.umidadeMin() == null ||
                    dto.faseLua() == null || dto.vento() == null ||
                    dto.presencaAnimalIntra() == null || dto.presencaAnimalPeri() == null ||
                    dto.galinheiro() == null || dto.estacaoAno() == null ||
                    dto.materiaOrganica() == null || dto.precipitacao() == null) {
                    throw new IllegalArgumentException("Todos os campos obrigatórios de FLEBOTOMINEOS devem ser preenchidos");
                }
                break;
            case TRIATOMINEOS:
                if (dto.peridomicilio() == null || dto.intradomicilio() == null ||
                    dto.comodoCaptura() == null || dto.vestigios() == null ||
                    dto.insetifugo() == null || dto.numeroInsetos() == null || dto.numeroInsetos() <= 0 ||
                    dto.condicao() == null) {
                    throw new IllegalArgumentException("Todos os campos obrigatórios de TRIATOMINEOS devem ser preenchidos");
                }
                break;
            case MOLUSCO:
                if (dto.numMoluscos() == null || dto.numMoluscos() <= 0 ||
                    dto.tipoMolusco() == null || dto.numMortos() == null ||
                    dto.exposicaoLuz() == null || dto.esmagamentoConcha() == null ||
                    dto.disseccao() == null || dto.resultado() == null) {
                    throw new IllegalArgumentException("Todos os campos devem ser preenchidos");
                }
                break;
            case LARVAS:
                if (dto.numLarvas() == null || dto.numLarvas() <= 0) {
                    throw new IllegalArgumentException("O campo numLarvas é obrigatório e deve ser maior que zero para LARVAS");
                }
                if (dto.tipoLarva() == null) {
                    throw new IllegalArgumentException("O campo tipoLarva é obrigatório para LARVAS");
                }
                break;
        }

        // Gera o protocolo antes de criar a amostra
        String protocolo = protocoloSequenceService.generateProtocolo("AMOSTRA");

        // Lógica de cadastro com anonimização e base legal
        switch (dto.tipoAmostra()) {
            case ESCORPIAO:
                Escorpioes escorpiao = tipoAmostraMapper.amostraDtoParaEscorpiao(dto);
                escorpiao.setProtocolo(protocolo);
                escorpiao.setStatus(StatusAmostra.PENDENTE);
                if (dto.enderecoDto() != null) {
                    escorpiao.setEndereco(enderecoMapper.toEntity(dto.enderecoDto()));
                    String enderecoCompleto = String.format("%s, %s, %s, %s",
                        dto.enderecoDto().logradouro(),
                        dto.enderecoDto().numero(),
                        dto.enderecoDto().bairro(),
                        dto.enderecoDto().municipio());
                    String enderecoAnonimizado = DataAnonymizer.anonymizeAddressPartial(enderecoCompleto);
                    escorpiao.getEndereco().setLogradouro(enderecoAnonimizado);
                }
                escorpiao.setBaseLegal("Cumprimento de obrigação legal (Art. 7º, II, LGPD)");
                if (escorpiao.getSofreuAcidente()) {
                    throw new AmostraInvalidaException("Amostras do tipo ESCORPIÃO não podem estar esmagadas.");
                }
                amostraEscorpiaoRepository.save(escorpiao);
                auditService.logAction("CADASTRO_AMOSTRA", protocolo, "Amostra ESCORPIAO cadastrada");
                return tipoAmostraMapper.escorpiaoEntidadeParaRetorno(escorpiao);
            case FLEBOTOMINEOS:
                Flebotomineos flebo = tipoAmostraMapper.amostraDtoParaFlebotomineos(dto);
                flebo.setProtocolo(protocolo);
                flebo.setStatus(StatusAmostra.PENDENTE);
                if (dto.enderecoDto() != null) {
                    flebo.setEndereco(enderecoMapper.toEntity(dto.enderecoDto()));
                    String enderecoCompleto = String.format("%s, %s, %s, %s",
                        dto.enderecoDto().logradouro(),
                        dto.enderecoDto().numero(),
                        dto.enderecoDto().bairro(),
                        dto.enderecoDto().municipio());
                    String enderecoAnonimizado = DataAnonymizer.anonymizeAddressPartial(enderecoCompleto);
                    flebo.getEndereco().setLogradouro(enderecoAnonimizado);
                }
                flebo.setBaseLegal("Cumprimento de obrigação legal (Art. 7º, II, LGPD)");
                amostraFlebotomineosRepository.save(flebo);
                auditService.logAction("CADASTRO_AMOSTRA", protocolo, "Amostra FLEBOTOMINEOS cadastrada");
                return tipoAmostraMapper.flebotomineosEntidadeParaRetorno(flebo);
            case TRIATOMINEOS:
                Triatomineos triato = tipoAmostraMapper.amostraDtoParaTriatomineos(dto);
                triato.setProtocolo(protocolo);
                triato.setStatus(StatusAmostra.PENDENTE);
                if (dto.enderecoDto() != null) {
                    triato.setEndereco(enderecoMapper.toEntity(dto.enderecoDto()));
                    String enderecoCompleto = String.format("%s, %s, %s, %s",
                        dto.enderecoDto().logradouro(),
                        dto.enderecoDto().numero(),
                        dto.enderecoDto().bairro(),
                        dto.enderecoDto().municipio());
                    String enderecoAnonimizado = DataAnonymizer.anonymizeAddressPartial(enderecoCompleto);
                    triato.getEndereco().setLogradouro(enderecoAnonimizado);
                }
                triato.setBaseLegal("Cumprimento de obrigação legal (Art. 7º, II, LGPD)");
                if (triato.getDataHora().isBefore(LocalDateTime.now().minusHours(48))) {
                    throw new AmostraInvalidaException("Amostras do tipo TRIATOMINEOS não podem ter mais de 48 horas desde a coleta.");
                }
                amostraTriatomineosRepository.save(triato);
                auditService.logAction("CADASTRO_AMOSTRA", protocolo, "Amostra TRIATOMINEOS cadastrada");
                return tipoAmostraMapper.triatomineosEntidadeParaRetorno(triato);
            case MOLUSCO:
                Molusco molusco = tipoAmostraMapper.amostraDtoParaMolusco(dto);
                molusco.setProtocolo(protocolo);
                molusco.setStatus(StatusAmostra.PENDENTE);
                if (dto.enderecoDto() != null) {
                    molusco.setEndereco(enderecoMapper.toEntity(dto.enderecoDto()));
                    String enderecoCompleto = String.format("%s, %s, %s, %s",
                        dto.enderecoDto().logradouro(),
                        dto.enderecoDto().numero(),
                        dto.enderecoDto().bairro(),
                        dto.enderecoDto().municipio());
                    String enderecoAnonimizado = DataAnonymizer.anonymizeAddressPartial(enderecoCompleto);
                    molusco.getEndereco().setLogradouro(enderecoAnonimizado);
                }
                molusco.setBaseLegal("Cumprimento de obrigação legal (Art. 7º, II, LGPD)");
                if (molusco.getDataHora().isBefore(LocalDateTime.now().minusHours(12))) {
                    throw new AmostraInvalidaException("Amostras do tipo MOLUSCO não podem ter mais de 12 horas desde a coleta.");
                }
                amostraMoluscoRepository.save(molusco);
                auditService.logAction("CADASTRO_AMOSTRA", protocolo, "Amostra MOLUSCO cadastrada");
                return tipoAmostraMapper.moluscoEntidadeParaRetorno(molusco);
            case LARVAS:
                Larvas larvas = tipoAmostraMapper.amostraDtoParaLarva(dto);
                larvas.setProtocolo(protocolo);
                larvas.setStatus(StatusAmostra.PENDENTE);
                if (dto.enderecoDto() != null) {
                    larvas.setEndereco(enderecoMapper.toEntity(dto.enderecoDto()));
                    String enderecoCompleto = String.format("%s, %s, %s, %s",
                        dto.enderecoDto().logradouro(),
                        dto.enderecoDto().numero(),
                        dto.enderecoDto().bairro(),
                        dto.enderecoDto().municipio());
                    String enderecoAnonimizado = DataAnonymizer.anonymizeAddressPartial(enderecoCompleto);
                    larvas.getEndereco().setLogradouro(enderecoAnonimizado);
                }
                larvas.setBaseLegal("Cumprimento de obrigação legal (Art. 7º, II, LGPD)");
                amostraLarvaRepository.save(larvas);
                auditService.logAction("CADASTRO_AMOSTRA", protocolo, "Amostra LARVAS cadastrada");
                return tipoAmostraMapper.larvasEntidadeParaRetorno(larvas);
            default:
                throw new RequisicaoInvalidaException("Tipo de amostra inválido: " + dto.tipoAmostra());
        }
    }

    @Transactional
    public Object editarAmostra(String protocolo, EditarAmostraDto dto) {
        Amostra amostra = amostraRepository.findByProtocolo(protocolo);
        if (amostra == null) {
            throw new RecursoNaoEncontradoException("Protocolo inválido ou inexistente");
        }

        if (amostra.getStatus() != StatusAmostra.PENDENTE) {
            throw new AmostraInvalidaException("A amostra não está em status PENDENTE");
        }

        if (amostra.getLote() != null && amostra.getLote().getStatusLote() == StatusLote.ENVIADO) {
            throw new AmostraInvalidaException("A amostra está associada a um lote ENVIADO");
        }

        if (dto.dataHora() != null) {
            amostra.setDataHora(dto.dataHora());
        }
        if (dto.enderecoDto() != null) {
            amostra.setEndereco(enderecoMapper.toEntity(dto.enderecoDto()));
        }

        switch (amostra.getTipoAmostra()) {
            case ESCORPIAO:
                Escorpioes escorpiao = amostraEscorpiaoRepository.findByProtocolo(protocolo);
                if (dto.quantidade() != null) {
                    if (dto.quantidade() <= 0) {
                        throw new IllegalArgumentException("A quantidade deve ser maior que zero para ESCORPIAO");
                    }
                    escorpiao.setQuantidade(dto.quantidade());
                }
                if (dto.sofreuAcidente() != null) {
                    if (dto.sofreuAcidente()) {
                        throw new AmostraInvalidaException("Amostras do tipo ESCORPIÃO não podem estar esmagadas.");
                    }
                    escorpiao.setSofreuAcidente(dto.sofreuAcidente());
                }
                amostraEscorpiaoRepository.save(escorpiao);
                return tipoAmostraMapper.escorpiaoEntidadeParaRetorno(escorpiao);
            case FLEBOTOMINEOS:
                Flebotomineos flebo = amostraFlebotomineosRepository.findByProtocolo(protocolo);
                amostraFlebotomineosRepository.save(flebo);
                return tipoAmostraMapper.flebotomineosEntidadeParaRetorno(flebo);
            case TRIATOMINEOS:
                Triatomineos triato = amostraTriatomineosRepository.findByProtocolo(protocolo);
                if (dto.dataHora() != null && dto.dataHora().isBefore(LocalDateTime.now().minusHours(48))) {
                    throw new AmostraInvalidaException("Amostras do tipo TRIATOMINEOS não podem ter mais de 48 horas desde a coleta.");
                }
                amostraTriatomineosRepository.save(triato);
                return tipoAmostraMapper.triatomineosEntidadeParaRetorno(triato);
            case MOLUSCO:
                Molusco molusco = amostraMoluscoRepository.findByProtocolo(protocolo);
                if (dto.dataHora() != null && dto.dataHora().isBefore(LocalDateTime.now().minusHours(12))) {
                    throw new AmostraInvalidaException("Amostras do tipo MOLUSCO não podem ter mais de 12 horas desde a coleta.");
                }
                amostraMoluscoRepository.save(molusco);
                return tipoAmostraMapper.moluscoEntidadeParaRetorno(molusco);
            case LARVAS:
                Larvas larvas = amostraLarvaRepository.findByProtocolo(protocolo);
                amostraLarvaRepository.save(larvas);
                return tipoAmostraMapper.larvasEntidadeParaRetorno(larvas);
            default:
                throw new RequisicaoInvalidaException("Tipo de amostra inválido: " + amostra.getTipoAmostra());
        }
    }

    @Transactional
    public void deletarAmostra(String protocolo) {
        Amostra amostra = amostraRepository.findByProtocolo(protocolo);
        if (amostra == null) {
            throw new RecursoNaoEncontradoException("Protocolo inválido ou inexistente");
        }

        if (amostra.getLote() != null) {
            throw new RequisicaoInvalidaException("A amostra está associada a um lote e não pode ser deletada.");
        }

        amostra.setStatus(StatusAmostra.DESCARTADA);
        amostraRepository.save(amostra);
    }

    public Amostra retornarAmostra(String protocolo) {
        Amostra amostra = amostraRepository.findByProtocolo(protocolo);
        if (amostra == null) {
            throw new RecursoNaoEncontradoException("Protocolo inválido ou inexistente");
        }
        return amostra;
    }

    public Object buscarAmostra(String protocolo) {
        Amostra amostra = amostraRepository.findByProtocolo(protocolo);
        if (amostra == null) {
            throw new RecursoNaoEncontradoException("Protocolo inválido ou inexistente");
        }
        switch (amostra.getTipoAmostra()) {
            case ESCORPIAO:
                Escorpioes escorpioes = amostraEscorpiaoRepository.findByProtocolo(protocolo);
                return tipoAmostraMapper.escorpiaoEntidadeParaRetorno(escorpioes);
            case FLEBOTOMINEOS:
                Flebotomineos flebo = amostraFlebotomineosRepository.findByProtocolo(protocolo);
                return tipoAmostraMapper.flebotomineosEntidadeParaRetorno(flebo);
            case TRIATOMINEOS:
                Triatomineos triato = amostraTriatomineosRepository.findByProtocolo(protocolo);
                return tipoAmostraMapper.triatomineosEntidadeParaRetorno(triato);
            case LARVAS:
                Larvas larvas = amostraLarvaRepository.findByProtocolo(protocolo);
                return tipoAmostraMapper.larvasEntidadeParaRetorno(larvas);
            case MOLUSCO:
                Molusco molusco = amostraMoluscoRepository.findByProtocolo(protocolo);
                return tipoAmostraMapper.moluscoEntidadeParaRetorno(molusco);
            default:
                throw new RecursoNaoEncontradoException("Tipo de amostra não encontrada");
        }
    }

    public List<ProtocoloListaAmostraDto> listarTodasAmostras() {
        List<Amostra> amostras = amostraRepository.findAll();
        return amostras.stream().map(tipoAmostraMapper::listagemAmostras).toList();
    }

    @Transactional
    public void adicionarLaudo(MultipartFile arquivo, String protocolo) throws IOException {
        if (arquivo.isEmpty() || arquivo.getContentType() == null || !arquivo.getContentType().equalsIgnoreCase("application/pdf")) {
            throw new IllegalArgumentException("Tipo de arquivo não suportado.");
        }
        Amostra amostra = amostraRepository.findByProtocolo(protocolo);
        if (amostra == null) {
            throw new RecursoNaoEncontradoException("Protocolo inválido ou inexistente");
        }
        amostra.setLaudo(arquivo.getBytes());
        amostraRepository.save(amostra);
    }

    @Transactional
    public Object analisarAmostra(String protocolo) {
        Amostra amostra = amostraRepository.findByProtocolo(protocolo);
        if (amostra == null) {
            throw new RecursoNaoEncontradoException("Protocolo inválido ou inexistente");
        }
        if (amostra.getStatus() == StatusAmostra.DESCARTADA) {
            throw new AmostraInvalidaException("A amostra possui o status DESCARTADA e não pode ser mais analisada");
        }
        amostra.setStatus(StatusAmostra.ANALISADA);
        amostraRepository.save(amostra);
        switch (amostra.getTipoAmostra()) {
            case ESCORPIAO:
                Escorpioes escorpioes = amostraEscorpiaoRepository.findByProtocolo(protocolo);
                return tipoAmostraMapper.escorpiaoEntidadeParaRetorno(escorpioes);
            case FLEBOTOMINEOS:
                Flebotomineos flebo = amostraFlebotomineosRepository.findByProtocolo(protocolo);
                return tipoAmostraMapper.flebotomineosEntidadeParaRetorno(flebo);
            case TRIATOMINEOS:
                Triatomineos triato = amostraTriatomineosRepository.findByProtocolo(protocolo);
                return tipoAmostraMapper.triatomineosEntidadeParaRetorno(triato);
            case LARVAS:
                Larvas larvas = amostraLarvaRepository.findByProtocolo(protocolo);
                return tipoAmostraMapper.larvasEntidadeParaRetorno(larvas);
            case MOLUSCO:
                Molusco molusco = amostraMoluscoRepository.findByProtocolo(protocolo);
                return tipoAmostraMapper.moluscoEntidadeParaRetorno(molusco);
            default:
                throw new RecursoNaoEncontradoException("Tipo de amostra não encontrada");
        }
    }

    @Transactional
    public Object anonimizarAmostra(String protocolo, String tipoAnonimizacao) {
        Amostra amostra = amostraRepository.findByProtocolo(protocolo);
        if (amostra == null) {
            throw new RecursoNaoEncontradoException("Protocolo inválido ou inexistente");
        }

        if (!tipoAnonimizacao.equalsIgnoreCase("PARCIAL") && !tipoAnonimizacao.equalsIgnoreCase("COMPLETA")) {
            throw new RequisicaoInvalidaException("Tipo de anonimização inválido. Use 'PARCIAL' ou 'COMPLETA'");
        }

        if (tipoAnonimizacao.equalsIgnoreCase("PARCIAL")) {
            amostra.setEndereco(DataAnonymizer.anonymizeEnderecoPartial(amostra.getEndereco()));
            amostra.setLaudo(null);
        } else {
            amostra.setEndereco(DataAnonymizer.anonymizeEnderecoComplete(amostra.getEndereco()));
            amostra.setLaudo(null);
        }

        amostraRepository.save(amostra);
        auditService.logAction("ANONIMIZACAO_AMOSTRA", protocolo, "Amostra anonimizada: " + tipoAnonimizacao);

        switch (amostra.getTipoAmostra()) {
            case ESCORPIAO:
                Escorpioes escorpioes = amostraEscorpiaoRepository.findByProtocolo(protocolo);
                return tipoAmostraMapper.escorpiaoEntidadeParaRetorno(escorpioes);
            case FLEBOTOMINEOS:
                Flebotomineos flebo = amostraFlebotomineosRepository.findByProtocolo(protocolo);
                return tipoAmostraMapper.flebotomineosEntidadeParaRetorno(flebo);
            case TRIATOMINEOS:
                Triatomineos triato = amostraTriatomineosRepository.findByProtocolo(protocolo);
                return tipoAmostraMapper.triatomineosEntidadeParaRetorno(triato);
            case LARVAS:
                Larvas larvas = amostraLarvaRepository.findByProtocolo(protocolo);
                return tipoAmostraMapper.larvasEntidadeParaRetorno(larvas);
            case MOLUSCO:
                Molusco molusco = amostraMoluscoRepository.findByProtocolo(protocolo);
                return tipoAmostraMapper.moluscoEntidadeParaRetorno(molusco);
            default:
                throw new RecursoNaoEncontradoException("Tipo de amostra não encontrada");
        }
    }

    @Transactional(readOnly = true)
    public Page<ProtocoloListaAmostraDto> filtrarAmostras(TipoAmostra tipoAmostra, StatusAmostra status, String municipio, Pageable pageable) {
        Specification<Amostra> spec = AmostraSpecification.filtrarAmostras(tipoAmostra, status, municipio);
        Page<Amostra> amostras = amostraRepository.findAll(spec, pageable);
        return amostras.map(tipoAmostraMapper::listagemAmostras);
    }
}