package com.example.squad2_suporte.Controller;

import com.example.squad2_suporte.Laudos.Laudo;
import com.example.squad2_suporte.repositorios.LaudoRepository;
import com.example.squad2_suporte.service.LaudoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/laudos/upload")
public class LaudoController {

    @Autowired
    private LaudoService laudoService;

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile arquivo) {

        try {
            Laudo laudo = laudoService.salvar(arquivo);
            return ResponseEntity.ok("Arquivo salvo. ID: " + laudo.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao salvar o arquivo.");
        }
    }

    @GetMapping("/laudos/download")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        return laudoService.findById(id)
                .map(laudo -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + laudo.getNome())
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(laudo.getDados()))
                .orElse(ResponseEntity.notFound().build());
    }

}
