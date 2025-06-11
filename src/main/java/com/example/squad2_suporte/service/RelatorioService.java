package com.example.squad2_suporte.service;

import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.Amostras.endereco.Endereco;
import com.example.squad2_suporte.Classes.Larvas; // Ajuste o pacote conforme necessário
import com.example.squad2_suporte.Lamina.Lamina;
import com.example.squad2_suporte.lote.Lote;
import com.example.squad2_suporte.config.exceptions.RecursoNaoEncontradoException;
import com.example.squad2_suporte.repositorios.LoteRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream; // Import adicionado

@Service
public class RelatorioService {

    @Autowired
    private LoteRepository loteRepository;

    public byte[] gerarRelatorioLote(String protocolo) {
        Lote lote = loteRepository.findByProtocolo(protocolo);
        if (lote == null) {
            throw new RecursoNaoEncontradoException("Lote não encontrado para o protocolo: " + protocolo);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        // Cabeçalho
        document.add(new Paragraph("Relatório de Lote - LACEN")
                .setBold()
                .setFontSize(16));
        document.add(new Paragraph("Protocolo: " + lote.getProtocolo()));
        document.add(new Paragraph("Status: " + lote.getStatusLote()));
        document.add(new Paragraph("Data de Criação: " + lote.getDataCriacao()));
        document.add(new Paragraph("\n"));

        // Listar amostras, se houver
        if (!lote.getAmostras().isEmpty()) {
            document.add(new Paragraph("Amostras Associadas:").setBold());
            for (Amostra amostra : lote.getAmostras()) {
                document.add(new Paragraph("Protocolo: " + amostra.getProtocolo()));
                document.add(new Paragraph("Tipo: " + amostra.getTipoAmostra()));
                document.add(new Paragraph("Município: " + amostra.getEndereco().getMunicipio()));
                // Ajuste para quantidade (verifica tipo de amostra)
                String quantidade = "N/A";
                if (amostra instanceof Larvas) {
                    quantidade = String.valueOf(((Larvas) amostra).getNumLarvas());
                } // Adicione outras subclasses conforme necessário, ex.: Escorpioes
                document.add(new Paragraph("Quantidade: " + quantidade));
                document.add(new Paragraph("Endereço de Captura: " + formatarEndereco(amostra.getEndereco())));
                document.add(new Paragraph("Data de Captura: " + amostra.getDataHora()));
                document.add(new Paragraph("Inseto/Larva: " + obterInsetoLarva(amostra)));
                document.add(new Paragraph("---"));
            }
        }

        // Listar lâminas, se houver
        if (!lote.getLaminas().isEmpty()) {
            document.add(new Paragraph("Lâminas Associadas:").setBold());
            for (Lamina lamina : lote.getLaminas()) {
                document.add(new Paragraph("Protocolo: " + lamina.getProtocolo()));
                document.add(new Paragraph("Status: " + lamina.getStatus()));
                document.add(new Paragraph("Endereço de Captura: " + lamina.getEnderecoCaptura()));
                document.add(new Paragraph("Número de Ovos: " + (lamina.getNumeroOvos() != null ? lamina.getNumeroOvos() : "N/A")));
                document.add(new Paragraph("Resultado: " + (lamina.getResultado() != null ? lamina.getResultado() : "N/A")));
                document.add(new Paragraph("---"));
            }
        }

        document.close();
        return baos.toByteArray();
    }

    private String formatarEndereco(Endereco endereco) {
        return String.format("%s, %s, %s, %s, %s",
                endereco.getLogradouro() != null ? endereco.getLogradouro() : "",
                endereco.getNumero() != null ? endereco.getNumero() : "",
                endereco.getBairro() != null ? endereco.getBairro() : "",
                endereco.getMunicipio(),
                endereco.getCep() != null ? endereco.getCep() : "");
    }

    private String obterInsetoLarva(Amostra amostra) {
        switch (amostra.getTipoAmostra()) {
            case ESCORPIAO:
                return "Escorpião";
            case TRIATOMINEOS:
                return "Triatomíneo";
            case FLEBOTOMINEOS:
                return "Flebotomíneo";
            case MOLUSCO:
                return "Molusco";
            case LARVAS:
                return "Larva - " + (amostra instanceof Larvas ? ((Larvas) amostra).getTipoLarva() : "N/A");
            default:
                return "N/A";
        }
    }
}