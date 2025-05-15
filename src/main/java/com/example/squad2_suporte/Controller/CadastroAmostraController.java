package com.example.squad2_suporte.Controller;

import com.example.squad2_suporte.Classes.Escorpioes;
import com.example.squad2_suporte.Classes.Flebotomineos;
import com.example.squad2_suporte.Classes.Triatomineos;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cadastro/amostra")
public class CadastroAmostraController {

    @Autowired
    private AmostraService amostraService;

    @Operation(summary = "Cadastrar uma amostra de Escorpião", description = "Metodo que cadastra uma nova amostra de Escorpião", tags = "Cadastrar Amostras")
    @PostMapping("escorpiao")
    public ResponseEntity<RetornoEscorpiaoDto> cadastrarEscorpiao(@RequestBody EscorpiaoDto dto) {
        var novaAmostra = amostraService.cadastrarAmostraEscorpioes(dto);
        return ResponseEntity.ok(novaAmostra);
    }

    @Operation(summary = "Cadastrar uma amostra de Flebotomineo", description = "Metodo que cadastra uma nova amostra de Flebotomineo", tags = "Cadastrar Amostras")
    @PostMapping("flebotomineo")
    public ResponseEntity<RetornoFlebotomineosDto> cadastrarFlebotomineos(@RequestBody FlebotomineosDto dto) {
        var novaAmostra = amostraService.cadastrarAmostraFlebotomineos(dto);
        return ResponseEntity.ok(novaAmostra);
    }

    @Operation(summary = "Cadastrar uma amostra de Triatomineo", description = "Metodo que cadastra uma nova amostra de Triatomineo", tags = "Cadastrar Amostras")
    @PostMapping("triatomineo")
    public ResponseEntity<RetornoTriatomineosDto> cadastrarTriatomineos(@RequestBody TriatomineosDto dto) {
        var novaAmostra = amostraService.cadastrarAmostraTriatomineos(dto);
        return ResponseEntity.ok(novaAmostra);
    }

}
