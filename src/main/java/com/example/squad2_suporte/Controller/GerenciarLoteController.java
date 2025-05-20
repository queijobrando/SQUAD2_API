package com.example.squad2_suporte.Controller;

import com.example.squad2_suporte.Amostras.mapper.LoteMapper;
import com.example.squad2_suporte.dto.lote.LoteDto;
import com.example.squad2_suporte.dto.lote.RetornoLoteDto;
import com.example.squad2_suporte.service.LoteService;
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

    @PostMapping("criar")
    public ResponseEntity<RetornoLoteDto> criarLote(@RequestBody LoteDto loteDto, UriComponentsBuilder uriComponentsBuilder){
        var lote = loteService.cadastrarLote(loteDto);

        var uri = uriComponentsBuilder.path("gerenciar/lote/criar/{id}").buildAndExpand(lote.getId()).toUri();

        return ResponseEntity.created(uri).body(loteMapper.entidadeParaRetorno(lote));
    }

    @GetMapping("lotesCadastrados")
    public ResponseEntity<List<RetornoLoteDto>> listarLotes(){
        var listaLotes = loteService.listarLotes();

        return ResponseEntity.ok(listaLotes);
    }

}
