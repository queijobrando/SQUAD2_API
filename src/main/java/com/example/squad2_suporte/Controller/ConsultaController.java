package com.example.squad2_suporte.Controller;

import com.example.squad2_suporte.service.OpcoesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("consulta")
public class ConsultaController {

    @Autowired
    private OpcoesService opcoesService;

    @GetMapping("/opcoes")
    public ResponseEntity<List<String>> listarOpcoes(@RequestParam String tipo,
                                                     @RequestParam String chave){
        var valores =  opcoesService.listarOpcoes(tipo, chave);

        return ResponseEntity.ok(valores);
    }

}
