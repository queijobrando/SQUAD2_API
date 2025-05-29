package com.example.squad2_suporte.Controller;

import com.example.squad2_suporte.Amostras.mapper.LoteMapper;
import com.example.squad2_suporte.dto.lote.EditarLoteDto;
import com.example.squad2_suporte.dto.lote.LoteDto;
import com.example.squad2_suporte.dto.lote.RetornoLoteDto;
import com.example.squad2_suporte.service.LoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("gerenciar/lote")
public class GerenciarLoteController {

    @Autowired
    private LoteService loteService;

    @Autowired
    private LoteMapper loteMapper;

    @Operation(summary = "Criar Lote", description = "Metodo para criar um novo lote", tags = "Gerenciar Lote")
    @PostMapping("criar")
    public ResponseEntity<RetornoLoteDto> criarLote(@RequestBody LoteDto loteDto, UriComponentsBuilder uriComponentsBuilder){
        var lote = loteService.cadastrarLote(loteDto);

        var uri = uriComponentsBuilder.path("gerenciar/lote/criar/{id}").buildAndExpand(lote.getId()).toUri();

        return ResponseEntity.created(uri).body(loteMapper.entidadeParaRetorno(lote));
    }

    @Operation(summary = "Listar Lotes", description = "Metodo para listar lotes cadastrados", tags = "Gerenciar Lote")
    @GetMapping("lotesCadastrados")
    public ResponseEntity<List<RetornoLoteDto>> listarLotes(){
        var listaLotes = loteService.listarLotes();

        if (listaLotes.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }

        return ResponseEntity.ok(listaLotes);
    }

    @Operation(
            summary = "Editar Lote",
            description = "Adiciona ou remove amostras de um lote específico com base na opção fornecida.",
            tags = "Gerenciar Lote",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Adicionar Amostra OU Lamina",
                                            value = """
                    {
                      "opcao": "ADICIONAR",
                      "protocolos": [
                        123, 124
                      ]
                    }
                    """
                                    ),
                                    @ExampleObject(
                                            name = "Remover Amostra OU Lamina",
                                            value = """
                    {
                      "opcao": "REMOVER",
                      "protocolos": [
                        123
                      ]
                    }
                    """
                                    )
                            }
                    )
            )
    )
    @PutMapping("/{protocolo}")
    public ResponseEntity<RetornoLoteDto> editarLote(@PathVariable Long protocolo, @RequestBody EditarLoteDto dto) {
        RetornoLoteDto lote = loteService.editarLote(dto, protocolo);
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


}
