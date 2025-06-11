package com.example.squad2_suporte.Controller;

import com.example.squad2_suporte.Amostras.mapper.LoteMapper;
import com.example.squad2_suporte.dto.lote.LoteDto;
import com.example.squad2_suporte.dto.lote.ProtocolosDto;
import com.example.squad2_suporte.dto.lote.RetornoLoteDto;
import com.example.squad2_suporte.service.LoteService;
import com.example.squad2_suporte.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("gerenciar/lote")
public class GerenciarLoteController {

    @Autowired
    private LoteService loteService;

    @Autowired
    private LoteMapper loteMapper;

    @Autowired
    private RelatorioService relatorioService;

    @Operation(
        summary = "Criar Lote",
        description = "Método para criar um novo lote",
        tags = "Gerenciar Lote",
        parameters = {
            @Parameter(name = "tipo", description = "Tipo do lote", required = true, in = ParameterIn.QUERY,
                       schema = @Schema(implementation = String.class, allowableValues = {"amostra", "lamina"}))
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                mediaType = "application/json"
            )
        )
    )
    @PostMapping("criar")
    public ResponseEntity<RetornoLoteDto> criarLote(
            @RequestParam("tipo") String tipo,
            @RequestBody ProtocolosDto protocolosDto,
            UriComponentsBuilder uriComponentsBuilder) {
        // Converter o tipo de String para o enum TipoLote
        com.example.squad2_suporte.enuns.TipoLote tipoLote;
        try {
            tipoLote = com.example.squad2_suporte.enuns.TipoLote.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tipo de lote inválido. Use 'amostra' ou 'lamina'");
        }
        // Criar LoteDto com o tipo obtido e os protocolos do ProtocolosDto
        LoteDto loteDto = new LoteDto(tipoLote, protocolosDto.protocolos());
        var lote = loteService.cadastrarLote(loteDto);
        var uri = uriComponentsBuilder.path("gerenciar/lote/criar/{id}").buildAndExpand(lote.getId()).toUri();
        return ResponseEntity.created(uri).body(loteMapper.entidadeParaRetorno(lote));
    }

    @Operation(summary = "Listar Lotes", description = "Método para listar lotes cadastrados", tags = "Gerenciar Lote")
    @GetMapping("lotesCadastrados")
    public ResponseEntity<List<RetornoLoteDto>> listarLotes() {
        var listaLotes = loteService.listarLotes();
        if (listaLotes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listaLotes);
    }

    @Operation(
        summary = "Editar Lote",
        description = "Adiciona ou remove amostras de um lote específico com base na opção fornecida.",
        tags = "Gerenciar Lote",
        parameters = {
            @Parameter(name = "opcao", description = "Opção de edição", required = true, in = ParameterIn.QUERY,
                       schema = @Schema(implementation = String.class, allowableValues = {"ADICIONAR", "REMOVER"}))
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                mediaType = "application/json"
            )
        )
    )
    @PutMapping("/{protocolo}")
    public ResponseEntity<RetornoLoteDto> editarLote(
            @PathVariable String protocolo,
            @RequestParam("opcao") String opcao,
            @RequestBody ProtocolosDto dto) {
        RetornoLoteDto lote = loteService.editarLote(dto, protocolo, opcao);
        return ResponseEntity.ok(lote);
    }

    @Operation(summary = "Enviar Lote", description = "Método para definir o status do lote como ENVIADO", tags = "Gerenciar Lote")
    @PutMapping("/{protocolo}/enviar")
    public ResponseEntity<RetornoLoteDto> enviarLote(@PathVariable String protocolo) {
        RetornoLoteDto lote = loteService.enviarLote(protocolo);
        return ResponseEntity.ok(lote);
    }

    @Operation(summary = "Buscar lotes por município", description = "Retorna lotes associados a amostras de um determinado município", tags = "Gerenciar Lote")
    @GetMapping("/buscar-por-municipio")
    public ResponseEntity<List<RetornoLoteDto>> buscarPorMunicipio(@RequestParam String municipio) {
        List<RetornoLoteDto> lotes = loteService.buscarLotesPorMunicipio(municipio);
        if (lotes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(lotes);
    }

    @Operation(summary = "Buscar Lote", description = "Método para buscar lote cadastrado", tags = "Gerenciar Lote")
    @GetMapping("/{protocolo}")
    public ResponseEntity<RetornoLoteDto> buscarLote(@PathVariable String protocolo) {
        var lote = loteService.buscarLote(protocolo);
        return ResponseEntity.ok(lote);
    }

    @Operation(summary = "Descartar Lote", description = "Método para descartar um lote, exige confirmação explícita", tags = "Gerenciar Lote")
    @DeleteMapping("/{protocolo}")
    public ResponseEntity<RetornoLoteDto> descartarLote(@PathVariable String protocolo, @RequestParam(defaultValue = "false") boolean confirm) {
        if (!confirm) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Confirmação obrigatória para excluir o lote. Esta ação é irreversível.");
        }
        RetornoLoteDto lote = loteService.descartarLote(protocolo);
        return ResponseEntity.ok(lote);
    }

    @Operation(
        summary = "Gerar Relatório de Lote (PDF)",
        description = "Gera um relatório em PDF do lote",
        tags = "Gerenciar Lote",
        responses = {
            @ApiResponse(responseCode = "200", description = "PDF gerado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_PDF_VALUE)),
            @ApiResponse(responseCode = "500", description = "Erro interno ao gerar PDF", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
        }
    )
    @GetMapping(value = "/{protocolo}/relatorio")
    public ResponseEntity<?> gerarRelatorioLote(@PathVariable String protocolo) {
        try {
            byte[] pdf = relatorioService.gerarRelatorioLote(protocolo);
            return ResponseEntity.ok()
                    .header("Content-Type", "application/pdf")
                    .header("Content-Disposition", "attachment; filename=relatorio_lote_" + protocolo + ".pdf")
                    .body(pdf);
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .header("Content-Type", "application/json")
                    .body("{\"error\": \"Erro ao gerar PDF: " + e.getMessage() + "\"}");
        }
    }
}