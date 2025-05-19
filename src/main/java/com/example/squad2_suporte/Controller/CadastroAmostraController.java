package com.example.squad2_suporte.Controller;

import com.example.squad2_suporte.Classes.Escorpioes;
import com.example.squad2_suporte.Classes.Flebotomineos;
import com.example.squad2_suporte.Classes.Triatomineos;
import com.example.squad2_suporte.dto.amostra.AmostraDto;
import com.example.squad2_suporte.dto.enviotipoamostras.EscorpiaoDto;
import com.example.squad2_suporte.dto.enviotipoamostras.FlebotomineosDto;
import com.example.squad2_suporte.dto.enviotipoamostras.TriatomineosDto;
import com.example.squad2_suporte.dto.retornotipoamostras.RetornoEscorpiaoDto;
import com.example.squad2_suporte.dto.retornotipoamostras.RetornoFlebotomineosDto;
import com.example.squad2_suporte.dto.retornotipoamostras.RetornoTriatomineosDto;
import com.example.squad2_suporte.service.AmostraService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cadastro/amostra")
public class CadastroAmostraController {

    @Autowired
    private AmostraService amostraService;

    @PostMapping
    public ResponseEntity<?> cadastrarAmostra(@RequestBody AmostraDto dto) {
        var resposta = amostraService.cadastrarAmostraUnificada(dto);
        return ResponseEntity.ok(resposta);
    }

    @DeleteMapping("/{protocolo}")
    public ResponseEntity<String> removerAmostra(@PathVariable Long protocolo) {
        amostraService.deletarAmostra(protocolo);
        return ResponseEntity.ok("Amostra removida!");
    }

    @GetMapping("/{protocolo}")
    public ResponseEntity<?> buscarAmostra(@PathVariable Long protocolo){
        var amostra = amostraService.buscarAmostra(protocolo);

        return ResponseEntity.ok(amostra);
    }

}
