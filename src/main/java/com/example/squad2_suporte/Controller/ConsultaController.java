package com.example.squad2_suporte.Controller;

import com.example.squad2_suporte.service.OpcoesService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("consulta")
public class ConsultaController {

    @Autowired
    private OpcoesService opcoesService;

    @Operation(summary = "Consultar opções dos formulários de Amostras", description = "Metodo para consultar as opções a serem preenchidas na requisição do cadastro dos tipos de amostras", tags = "Consultas")
    @GetMapping("/opcoes")
    public ResponseEntity<Map<String, List<String>>> listarOpcoes(@RequestParam String tipo){
        return ResponseEntity.ok(opcoesService.listarPorTipo(tipo));
    }

}
