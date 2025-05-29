package com.example.squad2_suporte.Controller;

import com.example.squad2_suporte.Amostras.mapper.LaminaMapper;
import com.example.squad2_suporte.dto.amostra.ProtocoloAmostraDto;
import com.example.squad2_suporte.dto.lamina.LaminaDto;
import com.example.squad2_suporte.dto.lamina.RetornoLaminaDto;
import com.example.squad2_suporte.service.LaminaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("cadastro/lamina")
public class CadastroLaminaController {

    @Autowired
    private LaminaService laminaService;

    @Autowired
    private LaminaMapper laminaMapper;

    @Operation(summary = "Cadastrar Lamina", description = "Metodo para cadastrar uma nova Lamina", tags = "Gerenciar Lamina")
    @PostMapping
    public ResponseEntity<RetornoLaminaDto> cadastrarLamina(@RequestBody LaminaDto dto, UriComponentsBuilder uriComponentsBuilder){
        var novaLamina = laminaService.cadastrarLamina(dto);

        var uri = uriComponentsBuilder.path("cadastro/lamina/{id}").buildAndExpand(novaLamina.getId()).toUri();

        return ResponseEntity.created(uri).body(laminaMapper.entidadeParaRetorno(novaLamina)); // 201 CREATED
    }

    @Operation(summary = "Deletar Lamina", description = "Metodo para deletar uma lamina existente pelo numero do protocolo", tags = "Gerenciar Lamina")
    @DeleteMapping("/{protocolo}")
    public ResponseEntity<String> removerLamina(@PathVariable Long protocolo) {
        laminaService.deletarLamina(protocolo);
        return ResponseEntity.ok("Lamina removida!");
    }

    @Operation(summary = "Buscar Lamina", description = "Metodo para buscar e exibir informações de uma lamina", tags = "Gerenciar Lamina")
    @GetMapping("/{protocolo}")
    public ResponseEntity<RetornoLaminaDto> buscarLamina(@PathVariable Long protocolo){
        var lamina = laminaService.buscarLamina(protocolo);

        return ResponseEntity.ok(laminaMapper.entidadeParaRetorno(lamina));
    }

    @Operation(summary = "Listar Laminas", description = "Metodo para buscar e exibir uma lista de laminas cadastradas", tags = "Gerenciar Lamina")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de laminas retornada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RetornoLaminaDto.class)
            )),
            @ApiResponse(responseCode = "204", description = "Nenhuma lamina encontrada", content = @Content),
    })
    @GetMapping
    public ResponseEntity<List<RetornoLaminaDto>> listarLaminas(){
        var lista = laminaService.listarLaminas();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }

        return ResponseEntity.ok(lista); // 200 OK com a lista
    }

    @Operation(summary = "Envio de laudo (PDF)", description = "Faz o envio do laudo em PDF associado a uma lamina via protocolo", tags = "Gerenciar Lamina")
    @PutMapping(path = "/{protocolo}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> adicionarLaudo(@PathVariable Long protocolo, @RequestParam("file") MultipartFile arquivo) throws IOException {
        laminaService.adicionarLaudo(arquivo, protocolo);
        return ResponseEntity.ok("Laudo adicionado.");
    }

    @Operation(summary = "Download de laudo (PDF)", description = "Faz o download do laudo em PDF associado a uma lamina via protocolo", tags = "Gerenciar Lamina")
    @GetMapping("/{protocolo}/laudo")
    public ResponseEntity<byte[]> baixarLaudo(@PathVariable Long protocolo){
        var lamina = laminaService.buscarLamina(protocolo);

        byte[] laudo = lamina.getLaudo();
        if (laudo == null || laudo.length == 0) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "laudo-" + protocolo + ".pdf");

        return new ResponseEntity<>(laudo, headers, HttpStatus.OK);

    }
}
