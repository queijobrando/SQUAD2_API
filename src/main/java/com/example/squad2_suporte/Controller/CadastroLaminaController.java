package com.example.squad2_suporte.Controller;

import com.example.squad2_suporte.Amostras.mapper.LaminaMapper;
import com.example.squad2_suporte.dto.lamina.EditarLaminaDto;
import com.example.squad2_suporte.dto.lamina.LaminaDto;
import com.example.squad2_suporte.dto.lamina.RetornoLaminaDto;
import com.example.squad2_suporte.service.LaminaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("gerenciar/lamina")
@SecurityRequirement(name = "bearerAuth") // Exige autenticação JWT para todos os endpoints
public class CadastroLaminaController {

    @Autowired
    private LaminaService laminaService;

    @Autowired
    private LaminaMapper laminaMapper;

    @Operation(
        summary = "Cadastrar Lâmina",
        description = "Cadastra uma nova lâmina. Os dados são processados em conformidade com a LGPD, utilizando como base legal o cumprimento de obrigação legal (Art. 7º, II, LGPD). Endereços de captura são anonimizados para proteger dados pessoais. Exige autenticação JWT.",
        tags = {"Gerenciar Lâmina"},
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Lâmina cadastrada com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RetornoLaminaDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 400 - Número de ovos inválido",
                        value = """
                        {
                          "mensagem": "O número de ovos não pode ser negativo",
                          "status": "BAD_REQUEST"
                        }
                        """
                    ),
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 400 - Dados inválidos",
                        value = """
                        {
                          "mensagem": "Dados inválidos",
                          "status": "BAD_REQUEST"
                        }
                        """
                    )
                }
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autorizado",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 401",
                        value = """
                        {
                          "mensagem": "Token JWT inválido ou ausente",
                          "status": "UNAUTHORIZED"
                        }
                        """
                    )
                }
            )
        )
    })
    @PostMapping
    public ResponseEntity<RetornoLaminaDto> cadastrarLamina(@Valid @RequestBody LaminaDto dto, UriComponentsBuilder uriComponentsBuilder) {
        var novaLamina = laminaService.cadastrarLamina(dto);
        var uri = uriComponentsBuilder.path("gerenciar/lamina/{id}").buildAndExpand(novaLamina.getId()).toUri();
        return ResponseEntity.created(uri).body(laminaMapper.entidadeParaRetorno(novaLamina)); // 201 CREATED
    }

    @Operation(
        summary = "Editar Lâmina",
        description = "Edita uma lâmina existente pelo número do protocolo, desde que esteja PENDENTE e não associada a um lote ENVIADO. Operação realizada em conformidade com a LGPD. Exige autenticação JWT.",
        tags = {"Gerenciar Lâmina"},
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lâmina editada com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RetornoLaminaDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Erro de validação ou lâmina não pode ser editada",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 400 - Lâmina não PENDENTE",
                        value = """
                        {
                          "mensagem": "A lâmina não está PENDENTE e não pode ser editada",
                          "status": "BAD_REQUEST"
                        }
                        """
                    ),
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 400 - Lâmina em lote ENVIADO",
                        value = """
                        {
                          "mensagem": "A lâmina está associada a um lote ENVIADO e não pode ser editada",
                          "status": "BAD_REQUEST"
                        }
                        """
                    ),
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 400 - Número de ovos inválido",
                        value = """
                        {
                          "mensagem": "O número de ovos não pode ser negativo",
                          "status": "BAD_REQUEST"
                        }
                        """
                    )
                }
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Protocolo inválido ou inexistente",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 404",
                        value = """
                        {
                          "mensagem": "Protocolo inválido ou inexistente",
                          "status": "NOT_FOUND"
                        }
                        """
                    )
                }
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autorizado",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 401",
                        value = """
                        {
                          "mensagem": "Token JWT inválido ou ausente",
                          "status": "UNAUTHORIZED"
                        }
                        """
                    )
                }
            )
        )
    })
    @PutMapping("/{protocolo}")
    public ResponseEntity<RetornoLaminaDto> editarLamina(@PathVariable String protocolo, @Valid @RequestBody EditarLaminaDto dto) {
        var laminaAtualizada = laminaService.editarLamina(protocolo, dto);
        return ResponseEntity.ok(laminaMapper.entidadeParaRetorno(laminaAtualizada));
    }

    @Operation(
        summary = "Descartar Lâmina",
        description = "Descarta uma lâmina existente pelo número do protocolo. Operação registrada para auditoria em conformidade com a LGPD. Exige autenticação JWT.",
        tags = {"Gerenciar Lâmina"},
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lâmina descartada com sucesso",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Sucesso",
                        value = """
                        "Lâmina descartada!"
                        """
                    )
                }
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Lâmina associada a um lote",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 400",
                        value = """
                        {
                          "mensagem": "A lâmina com protocolo {protocolo} está associada a um lote e não pode ser deletada.",
                          "status": "BAD_REQUEST"
                        }
                        """
                    )
                }
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Protocolo inválido ou inexistente",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 404",
                        value = """
                        {
                          "mensagem": "Protocolo inválido ou inexistente",
                          "status": "NOT_FOUND"
                        }
                        """
                    )
                }
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autorizado",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 401",
                        value = """
                        {
                          "mensagem": "Token JWT inválido ou ausente",
                          "status": "UNAUTHORIZED"
                        }
                        """
                    )
                }
            )
        )
    })
    @DeleteMapping("/{protocolo}")
    public ResponseEntity<String> removerLamina(@PathVariable String protocolo) {
        laminaService.deletarLamina(protocolo);
        return ResponseEntity.ok("Lâmina descartada!");
    }

    @Operation(
        summary = "Buscar Lâmina",
        description = "Busca e exibe informações de uma lâmina pelo protocolo. Acesso restrito ao município autenticado, em conformidade com a LGPD. Exige autenticação JWT.",
        tags = {"Gerenciar Lâmina"},
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lâmina encontrada",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RetornoLaminaDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Protocolo inválido ou inexistente",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 404",
                        value = """
                        {
                          "mensagem": "Protocolo inválido ou inexistente",
                          "status": "NOT_FOUND"
                        }
                        """
                    )
                }
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autorizado",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 401",
                        value = """
                        {
                          "mensagem": "Token JWT inválido ou ausente",
                          "status": "UNAUTHORIZED"
                        }
                        """
                    )
                }
            )
        )
    })
    @GetMapping("/{protocolo}")
    public ResponseEntity<RetornoLaminaDto> buscarLamina(@PathVariable String protocolo) {
        var lamina = laminaService.buscarLamina(protocolo);
        return ResponseEntity.ok(laminaMapper.entidadeParaRetorno(lamina));
    }

    @Operation(
        summary = "Listar Lâminas",
        description = "Lista todas as lâminas cadastradas pelo município autenticado, em conformidade com a LGPD. Exige autenticação JWT.",
        tags = {"Gerenciar Lâmina"},
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de lâminas retornada com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RetornoLaminaDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "204",
            description = "Nenhuma lâmina encontrada",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autorizado",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 401",
                        value = """
                        {
                          "mensagem": "Token JWT inválido ou ausente",
                          "status": "UNAUTHORIZED"
                        }
                        """
                    )
                }
            )
        )
    })
    @GetMapping
    public ResponseEntity<List<RetornoLaminaDto>> listarLaminas() {
        var lista = laminaService.listarLaminas();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(lista); // 200 OK com a lista
    }

    @Operation(
        summary = "Envio de Laudo (PDF)",
        description = "Associa um laudo em PDF a uma lâmina via protocolo. Operação registrada para auditoria em conformidade com a LGPD. Exige autenticação JWT.",
        tags = {"Gerenciar Lâmina"},
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Laudo adicionado com sucesso",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Sucesso",
                        value = """
                        "Laudo adicionado."
                        """
                    )
                }
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Arquivo inválido ou não é PDF",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 400",
                        value = """
                        {
                          "mensagem": "Tipo de arquivo não suportado.",
                          "status": "BAD_REQUEST"
                        }
                        """
                    )
                }
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Protocolo inválido ou inexistente",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 404",
                        value = """
                        {
                          "mensagem": "Protocolo inválido ou inexistente",
                          "status": "NOT_FOUND"
                        }
                        """
                    )
                }
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autorizado",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 401",
                        value = """
                        {
                          "mensagem": "Token JWT inválido ou ausente",
                          "status": "UNAUTHORIZED"
                        }
                        """
                    )
                }
            )
        )
    })
    @PutMapping(path = "/{protocolo}/laudo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> adicionarLaudo(@PathVariable String protocolo, @RequestParam("file") MultipartFile arquivo) throws IOException {
        laminaService.adicionarLaudo(arquivo, protocolo);
        return ResponseEntity.ok("Laudo adicionado.");
    }

    @Operation(
        summary = "Analisar Lâmina",
        description = "Define o status da lâmina como ANALISADA. Operação registrada para auditoria em conformidade com a LGPD. Exige autenticação JWT.",
        tags = {"Gerenciar Lâmina"},
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lâmina analisada com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RetornoLaminaDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Lâmina não pode ser analisada",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 400",
                        value = """
                        {
                          "mensagem": "A lâmina possui o status DESCARTADA e por tanto não pode ser mais ANALISADA",
                          "status": "BAD_REQUEST"
                        }
                        """
                    )
                }
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Protocolo inválido ou inexistente",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 404",
                        value = """
                        {
                          "mensagem": "Protocolo inválido ou inexistente",
                          "status": "NOT_FOUND"
                        }
                        """
                    )
                }
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autorizado",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 401",
                        value = """
                        {
                          "mensagem": "Token JWT inválido ou ausente",
                          "status": "UNAUTHORIZED"
                        }
                        """
                    )
                }
            )
        )
    })
    @PutMapping("/{protocolo}/analisar")
    public ResponseEntity<RetornoLaminaDto> analisarLamina(@PathVariable String protocolo) {
        var lamina = laminaService.analisarLamina(protocolo);
        return ResponseEntity.ok(laminaMapper.entidadeParaRetorno(lamina));
    }

    @Operation(
        summary = "Download de Laudo (PDF)",
        description = "Faz o download do laudo em PDF associado a uma lâmina via protocolo. Acesso restrito ao município autenticado, em conformidade com a LGPD. Exige autenticação JWT.",
        tags = {"Gerenciar Lâmina"},
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Laudo retornado com sucesso",
            content = @Content(
                mediaType = "application/pdf"
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Laudo ou protocolo não encontrado",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 404",
                        value = """
                        {
                          "mensagem": "Protocolo inválido ou inexistente",
                          "status": "NOT_FOUND"
                        }
                        """
                    )
                }
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autorizado",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 401",
                        value = """
                        {
                          "mensagem": "Token JWT inválido ou ausente",
                          "status": "UNAUTHORIZED"
                        }
                        """
                    )
                }
            )
        )
    })
    @GetMapping("/{protocolo}/laudo")
    public ResponseEntity<byte[]> baixarLaudo(@PathVariable String protocolo) {
        var lamina = laminaService.buscarLamina(protocolo);

        byte[] laudo = lamina.getLaudo();
        if (laudo == null || laudo.length == 0) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "laudo-" + protocolo + ".pdf");

        return new ResponseEntity<>(laudo, headers, HttpStatus.OK);
    }

    @Operation(
        summary = "Anonimizar Lâmina",
        description = "Anonimiza uma lâmina existente pelo número do protocolo, aplicando anonimização parcial ou completa. Operação registrada para auditoria em conformidade com a LGPD. Exige autenticação JWT.",
        tags = {"Gerenciar Lâmina"},
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lâmina anonimizada com sucesso",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = RetornoLaminaDto.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Tipo de anonimização inválido",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 400",
                        value = """
                        {
                          "mensagem": "Tipo de anonimização inválido. Use 'PARCIAL' ou 'COMPLETA'",
                          "status": "BAD_REQUEST"
                        }
                        """
                    )
                }
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Protocolo inválido ou inexistente",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 404",
                        value = """
                        {
                          "mensagem": "Protocolo inválido ou inexistente",
                          "status": "NOT_FOUND"
                        }
                        """
                    )
                }
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Não autorizado",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Erro 401",
                        value = """
                        {
                          "mensagem": "Token JWT inválido ou ausente",
                          "status": "UNAUTHORIZED"
                        }
                        """
                    )
                }
            )
        )
    })
    @PutMapping("/{protocolo}/anonimizar")
    public ResponseEntity<RetornoLaminaDto> anonimizarLamina(@PathVariable String protocolo, @RequestParam String tipoAnonimizacao) {
        var laminaAnonimizada = laminaService.anonimizarLamina(protocolo, tipoAnonimizacao);
        return ResponseEntity.ok(laminaMapper.entidadeParaRetorno(laminaAnonimizada));
    }
}