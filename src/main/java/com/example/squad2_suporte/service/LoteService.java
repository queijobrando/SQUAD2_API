package com.example.squad2_suporte.service;

import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.Amostras.mapper.LoteMapper;
import com.example.squad2_suporte.Lamina.Lamina;
import com.example.squad2_suporte.config.exceptions.AmostraInvalidaException;
import com.example.squad2_suporte.config.exceptions.LoteInvalidoException;
import com.example.squad2_suporte.config.exceptions.RecursoNaoEncontradoException;
import com.example.squad2_suporte.config.exceptions.RequisicaoInvalidaException;
import com.example.squad2_suporte.dto.lote.LoteDto;
import com.example.squad2_suporte.dto.lote.ProtocolosDto;
import com.example.squad2_suporte.dto.lote.RetornoLoteDto;
import com.example.squad2_suporte.enuns.StatusAmostra;
import com.example.squad2_suporte.enuns.StatusLote;
import com.example.squad2_suporte.lote.Lote;
import com.example.squad2_suporte.enuns.TipoLote;
import com.example.squad2_suporte.repositorios.AmostraRepository;
import com.example.squad2_suporte.repositorios.LaminaRepository;
import com.example.squad2_suporte.repositorios.LoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    @Autowired
    private ProtocoloSequenceService protocoloSequenceService;

    @Autowired
    private AuditService auditService;

    @Transactional
    public Lote cadastrarLote(LoteDto loteDto) {
        if (loteDto.tipo() == null) {
            throw new RequisicaoInvalidaException("O tipo do lote é obrigatório");
        }
        if (loteDto.protocolos() == null || loteDto.protocolos().isEmpty()) {
            throw new LoteInvalidoException("É necessário ao menos um protocolo para criar um lote");
        }

        List<Amostra> amostras = new ArrayList<>();
        List<Lamina> laminas = new ArrayList<>();

        if (loteDto.tipo() == TipoLote.AMOSTRA) {
            amostras = amostraRepository.findAllByProtocoloIn(loteDto.protocolos());
            if (amostras.size() != loteDto.protocolos().size()) {
                throw new RequisicaoInvalidaException("Alguma amostra não foi encontrada");
            }
        } else if (loteDto.tipo() == TipoLote.LAMINA) {
            laminas = laminaRepository.findAllByProtocoloIn(loteDto.protocolos());
            if (laminas.size() != loteDto.protocolos().size()) {
                throw new RequisicaoInvalidaException("Alguma lâmina não foi encontrada");
            }
        } else {
            throw new RequisicaoInvalidaException("Tipo de lote inválido");
        }

        Lote lote = new Lote();
        lote.setProtocolo(protocoloSequenceService.generateProtocolo("LOTE"));
        lote.setTipo(loteDto.tipo());
        lote.setStatusLote(StatusLote.PENDENTE);
        lote.setDataCriacao(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));

        if (!amostras.isEmpty()) {
            for (Amostra amostra : amostras) {
                if (amostra.getLote() != null) {
                    throw new AmostraInvalidaException("A amostra com protocolo " + amostra.getProtocolo() + " já possui um lote cadastrado");
                }
                amostra.setLote(lote);
                lote.getAmostras().add(amostra);
            }
        } else if (!laminas.isEmpty()) {
            for (Lamina lamina : laminas) {
                if (lamina.getLote() != null) {
                    throw new AmostraInvalidaException("A lâmina com protocolo " + lamina.getProtocolo() + " já possui um lote cadastrado");
                }
                lamina.setLote(lote);
                lote.getLaminas().add(lamina);
            }
        }

        Lote savedLote = loteRepository.save(lote);
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        auditService.logAlteracaoLote(user, savedLote, "CRIAR");
        return savedLote;
    }

    public List<RetornoLoteDto> listarLotes() {
        return loteRepository.findAll().stream()
                .map(loteMapper::entidadeParaRetorno)
                .toList();
    }

    public RetornoLoteDto buscarLote(String protocolo) {
        Lote lote = loteRepository.findByProtocolo(protocolo);
        if (lote == null) {
            throw new RecursoNaoEncontradoException("Protocolo inválido ou inexistente");
        }
        return loteMapper.entidadeParaRetorno(lote);
    }

    @Transactional
    public RetornoLoteDto editarLote(ProtocolosDto dto, String loteProtocolo, String opcao) {
        if (dto.protocolos() == null || dto.protocolos().isEmpty()) {
            throw new LoteInvalidoException("É necessário ao menos um protocolo para editar um lote");
        }

        Lote lote = loteRepository.findByProtocolo(loteProtocolo);
        if (lote == null) {
            throw new RecursoNaoEncontradoException("Lote com protocolo " + loteProtocolo + " não encontrado");
        }

        if (lote.getStatusLote() == StatusLote.DESCARTADO) {
            throw new LoteInvalidoException("O lote inserido já foi DESCARTADO e por isso não pode ser editado");
        }

        // Validação para evitar mistura de tipos
        if (lote.getTipo() == TipoLote.AMOSTRA) {
            List<Amostra> amostras = amostraRepository.findAllByProtocoloIn(dto.protocolos());
            if (amostras.size() != dto.protocolos().size()) {
                List<Lamina> laminas = laminaRepository.findAllByProtocoloIn(dto.protocolos());
                if (!laminas.isEmpty()) {
                    throw new RequisicaoInvalidaException("Não é possível adicionar lâminas a um lote de amostras");
                }
                throw new RequisicaoInvalidaException("Alguma amostra não foi encontrada");
            }
        } else if (lote.getTipo() == TipoLote.LAMINA) {
            List<Lamina> laminas = laminaRepository.findAllByProtocoloIn(dto.protocolos());
            if (laminas.size() != dto.protocolos().size()) {
                List<Amostra> amostras = amostraRepository.findAllByProtocoloIn(dto.protocolos());
                if (!amostras.isEmpty()) {
                    throw new RequisicaoInvalidaException("Não é possível adicionar amostras a um lote de lâminas");
                }
                throw new RequisicaoInvalidaException("Alguma lâmina não foi encontrada");
            }
        } else {
            throw new RequisicaoInvalidaException("Tipo de lote inválido");
        }

        List<Amostra> listaAmostras = new ArrayList<>();
        List<Lamina> listaLaminas = new ArrayList<>();

        if (lote.getTipo() == TipoLote.AMOSTRA) {
            listaAmostras = amostraRepository.findAllByProtocoloIn(dto.protocolos());
        } else if (lote.getTipo() == TipoLote.LAMINA) {
            listaLaminas = laminaRepository.findAllByProtocoloIn(dto.protocolos());
        }

        switch (opcao.toUpperCase()) {
            case "ADICIONAR":
                if (lote.getTipo() == TipoLote.AMOSTRA) {
                    for (Amostra amostra : listaAmostras) {
                        if (amostra.getLote() != null) {
                            throw new AmostraInvalidaException("A amostra com protocolo " + amostra.getProtocolo() + " já possui um lote cadastrado");
                        }
                        amostra.setLote(lote);
                        lote.getAmostras().add(amostra);
                    }
                } else if (lote.getTipo() == TipoLote.LAMINA) {
                    for (Lamina lamina : listaLaminas) {
                        if (lamina.getLote() != null) {
                            throw new AmostraInvalidaException("A lâmina com protocolo " + lamina.getProtocolo() + " já possui um lote cadastrado");
                        }
                        lamina.setLote(lote);
                        lote.getLaminas().add(lamina);
                    }
                }
                break;
            case "REMOVER":
                if (lote.getTipo() == TipoLote.AMOSTRA) {
                    for (Amostra amostra : listaAmostras) {
                        if (!lote.getAmostras().contains(amostra)) {
                            throw new AmostraInvalidaException("A amostra com protocolo " + amostra.getProtocolo() + " não pertence a este lote");
                        }
                        amostra.setLote(null);
                        lote.getAmostras().remove(amostra);
                    }
                } else if (lote.getTipo() == TipoLote.LAMINA) {
                    for (Lamina lamina : listaLaminas) {
                        if (!lote.getLaminas().contains(lamina)) {
                            throw new AmostraInvalidaException("A lâmina com protocolo " + lamina.getProtocolo() + " não pertence a este lote");
                        }
                        lamina.setLote(null);
                        lote.getLaminas().remove(lamina);
                    }
                }
                break;
            default:
                throw new RequisicaoInvalidaException("Opção inválida: " + opcao);
        }

        RetornoLoteDto retorno = loteMapper.entidadeParaRetorno(lote);
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        auditService.logAlteracaoLote(user, lote, "EDITAR");
        return retorno;
    }

    public List<RetornoLoteDto> buscarLotesPorMunicipio(String municipio) {
        List<Lote> lotes = loteRepository.findByMunicipio(municipio);
        return lotes.stream()
                .map(loteMapper::entidadeParaRetorno)
                .toList();
    }

    @Transactional
    public RetornoLoteDto enviarLote(String protocolo) {
        Lote lote = loteRepository.findByProtocolo(protocolo);
        if (lote == null) {
            throw new LoteInvalidoException("Protocolo inválido ou inexistente");
        }

        if (lote.getStatusLote() == StatusLote.DESCARTADO) {
            throw new LoteInvalidoException("O lote inserido já foi DESCARTADO e por isso não pode ser enviado");
        } else if (lote.getStatusLote() == StatusLote.ENVIADO) {
            throw new LoteInvalidoException("O lote inserido já foi enviado para processamento");
        }

        List<Amostra> amostras = lote.getAmostras();
        List<Lamina> laminas = lote.getLaminas();

        for (Amostra amostra : amostras) {
            if (amostra.getStatus() == StatusAmostra.DESCARTADA) {
                throw new AmostraInvalidaException("A amostra de protocolo " + amostra.getProtocolo() + " foi descartada e não pode ser enviada");
            } else if (amostra.getStatus() == StatusAmostra.PENDENTE) {
                throw new AmostraInvalidaException("A amostra de protocolo " + amostra.getProtocolo() + " está PENDENTE e precisa ser ANALISADA antes do envio");
            }
        }

        for (Lamina lamina : laminas) {
            if (lamina.getStatus() == StatusAmostra.DESCARTADA) {
                throw new AmostraInvalidaException("A lâmina de protocolo " + lamina.getProtocolo() + " foi descartada e não pode ser enviada");
            } else if (lamina.getStatus() == StatusAmostra.PENDENTE) {
                throw new AmostraInvalidaException("A lâmina de protocolo " + lamina.getProtocolo() + " está PENDENTE e precisa ser ANALISADA antes do envio");
            }
        }

        lote.setStatusLote(StatusLote.ENVIADO);
        loteRepository.save(lote);
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        auditService.logAlteracaoLote(user, lote, "ENVIAR");
        return loteMapper.entidadeParaRetorno(lote);
    }

    @Transactional
    public RetornoLoteDto descartarLote(String protocolo) {
        Lote lote = loteRepository.findByProtocolo(protocolo);
        if (lote == null) {
            throw new LoteInvalidoException("Protocolo inválido ou inexistente");
        }

        if (lote.getStatusLote() == StatusLote.ENVIADO) {
            throw new LoteInvalidoException("O lote inserido já foi enviado para processamento, e por tanto, não pode ser descartado");
        }

        lote.setStatusLote(StatusLote.DESCARTADO);
        loteRepository.save(lote);
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        auditService.logAlteracaoLote(user, lote, "DESCARTAR");
        return loteMapper.entidadeParaRetorno(lote);
    }
}