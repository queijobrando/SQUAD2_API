package com.example.squad2_suporte.Controller;

import com.example.squad2_suporte.dto.lamina.LaminaDto;
import com.example.squad2_suporte.dto.lamina.RetornoLaminaDto;
import com.example.squad2_suporte.service.LaminaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cadastro/lamina")
public class CadastroLaminaController {

    @Autowired
    private LaminaService laminaService;

    @PostMapping
    public ResponseEntity<RetornoLaminaDto> cadastrarLamina(@RequestBody LaminaDto dto){
        var novaLamina = laminaService.cadastrarLamina(dto);
        return ResponseEntity.ok(novaLamina);
    }
}
