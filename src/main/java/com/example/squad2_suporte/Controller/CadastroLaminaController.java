package com.example.squad2_suporte.Controller;

import com.example.squad2_suporte.Amostras.mapper.LaminaMapper;
import com.example.squad2_suporte.dto.lamina.LaminaDto;
import com.example.squad2_suporte.dto.lamina.RetornoLaminaDto;
import com.example.squad2_suporte.service.LaminaService;
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

    @PostMapping
    public ResponseEntity<RetornoLaminaDto> cadastrarLamina(@RequestBody LaminaDto dto, UriComponentsBuilder uriComponentsBuilder){
        var novaLamina = laminaService.cadastrarLamina(dto);

        var uri = uriComponentsBuilder.path("cadastro/lamina/{id}").buildAndExpand(novaLamina.getId()).toUri();

        return ResponseEntity.created(uri).body(laminaMapper.entidadeParaRetorno(novaLamina)); // 201 CREATED
    }

    @DeleteMapping("/{protocolo}")
    public ResponseEntity<String> removerLamina(@PathVariable Long protocolo) {
        laminaService.deletarLamina(protocolo);
        return ResponseEntity.ok("Lamina removida!");
    }

    @GetMapping("/{protocolo}")
    public ResponseEntity<RetornoLaminaDto> buscarLamina(@PathVariable Long protocolo){
        var lamina = laminaService.buscarLamina(protocolo);

        return ResponseEntity.ok(laminaMapper.entidadeParaRetorno(lamina));
    }
}
