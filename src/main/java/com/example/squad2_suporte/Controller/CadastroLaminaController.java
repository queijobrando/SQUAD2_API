package com.example.squad2_suporte.Controller;

import com.example.squad2_suporte.Amostras.mapper.LaminaMapper;
import com.example.squad2_suporte.dto.lamina.LaminaDto;
import com.example.squad2_suporte.dto.lamina.RetornoLaminaDto;
import com.example.squad2_suporte.service.LaminaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
}
