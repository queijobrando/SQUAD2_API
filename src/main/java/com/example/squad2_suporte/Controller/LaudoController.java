package com.example.squad2_suporte.Controller;

import com.example.squad2_suporte.Laudos.Laudo;
import com.example.squad2_suporte.service.LaudoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/laudos/upload")
public class LaudoController {

    @Autowired
    private LaudoService laudoService;

    @Operation(summary = "Upload de laudo (PDF)", description = "Metodo para fazer upload do laudo em PDF", tags = "Gerenciar Laudo")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> upload(@Parameter(description = "Arquivo PDF", content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE))
                                             @RequestParam("file") MultipartFile arquivo) {
        try {
            Laudo laudo = laudoService.salvar(arquivo);
            return ResponseEntity.ok("Arquivo salvo. ID: " + laudo.getId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao salvar o arquivo.");
        }
    }

    @Operation(summary = "Download de laudo (PDF)", description = "Metodo para fazer download do laudo em PDF", tags = "Gerenciar Laudo")
    @GetMapping("/laudos/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        return laudoService.findById(id)
                .map(laudo -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + laudo.getNome())
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(laudo.getDados()))
                .orElse(ResponseEntity.notFound().build());
    }

}
