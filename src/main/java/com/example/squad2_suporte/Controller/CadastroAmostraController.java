package com.example.squad2_suporte.Controller;

import com.example.squad2_suporte.config.RestErrorMessage;
import com.example.squad2_suporte.dto.amostra.AmostraDto;
import com.example.squad2_suporte.dto.amostra.ProtocoloAmostraDto;
import com.example.squad2_suporte.dto.amostra.ProtocoloListaAmostraDto;
import com.example.squad2_suporte.dto.retornotipoamostras.RetornoEscorpiaoDto;
import com.example.squad2_suporte.dto.retornotipoamostras.RetornoIdAmostras;
import com.example.squad2_suporte.service.AmostraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
@RequestMapping("gerenciar/amostra")
public class CadastroAmostraController {

    @Autowired
    private AmostraService amostraService;

    @Operation(
            summary = "Cadastrar amostra",
            description = "Metodo para cadastrar uma amostra",
            tags = "Gerenciar Amostras",
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Escorpião",
                                            value = """
                        {
                          "tipoAmostra": "ESCORPIAO",
                          "dataHora": "2025-05-22T10:30:00",
                          "enderecoDto": {
                            "logradouro": "Rua Exemplo",
                            "numero": "123",
                            "bairro": "Centro",
                            "cep": "87564330",
                            "cidade": "Cidade Exemplo",
                            "municipio": "Aracaju",
                            "complemento": "Perto de tal lugar"
                          },
                          "quantidade": 2,
                          "sofreuAcidente": false
                        }
                        """
                                    ),
                                    @ExampleObject(
                                            name = "Flebotomíneo",
                                            value = """
                        {
                          "tipoAmostra": "FLEBOTOMINEOS",
                          "dataHora": "2025-05-22T10:30:00",
                          "enderecoDto": {
                            "logradouro": "Rua Exemplo",
                            "numero": "123",
                            "bairro": "Centro",
                            "cep": "87564330",
                            "cidade": "Cidade Exemplo",
                            "municipio": "Aracaju",
                            "complemento": "Perto de tal lugar"
                          },
                          "classificacaoAreaLT": "SURTO",
                          "classificacaoAreaLV": "VULNERAVELNRECEPTIVO",
                          "tipoAtividadeLT": "FOCO",
                          "tipoAtividadeLV": "LEVANTAMENTO",
                          "tipoVegetacao": "SAVANA",
                          "distanciaVegetacao": 150,
                          "temperaturaChegada": "25.0",
                          "temperaturaSaida": "22.0",
                          "temperaturaMax": "30.0",
                          "temperaturaMin": "20.0",
                          "umidadeChegada": "80%",
                          "umidadeSaida": "70%",
                          "umidadeMax": "90%",
                          "umidadeMin": "60%",
                          "faseLua": "CHEIA",
                          "vento": "MEDIO",
                          "presencaAnimalIntra": "AVES",
                          "presencaAnimalPeri": "AVES",
                          "galinheiro": "SIM",
                          "estacaoAno": "VERAO",
                          "materiaOrganica": "SIM",
                          "precipitacao": "SIM",
                          "observacao": "Coleta realizada em área urbana"
                        }
                        """
                                    ),
                                    @ExampleObject(
                                            name = "Larvas",
                                            value = """
                        {
                          "tipoAmostra": "LARVAS",
                          "dataHora": "2025-05-22T10:30:00",
                          "enderecoDto": {
                            "logradouro": "Rua Exemplo",
                            "numero": "123",
                            "bairro": "Centro",
                            "cep": "87564330",
                            "cidade": "Cidade Exemplo",
                            "municipio": "Aracaju",
                            "complemento": "Perto de tal lugar"
                          },
                          "numLarvas": 20,
                          "tipoLarva": "A_EGYPYTI",
                          "tipoImovel": "Residencial"
                        }
                        """
                                    ),
                                    @ExampleObject(
                                            name = "Molusco",
                                            value = """
                        {
                          "tipoAmostra": "MOLUSCO",
                          "dataHora": "2025-05-22T10:30:00",
                          "enderecoDto": {
                            "logradouro": "Rua Exemplo",
                            "numero": "123",
                            "bairro": "Centro",
                            "cep": "87564330",
                            "cidade": "Cidade Exemplo",
                            "municipio": "Aracaju",
                            "complemento": "Perto de tal lugar"
                          },
                          "colecaoHidrica": "Lagoa Azul",
                          "numMoluscos": 12,
                          "tipoMolusco": "GLABRATA",
                          "numMortos": 2,
                          "exposicaoLuz": true,
                          "esmagamentoConcha": false,
                          "disseccao": true,
                          "resultado": "POSITIVO"
                        }
                        """
                                    ),
                                    @ExampleObject(
                                            name = "Triatomíneo",
                                            value = """
                        {
                          "tipoAmostra": "TRIATOMINEOS",
                          "dataHora": "2025-05-22T10:30:00",
                          "enderecoDto": {
                            "logradouro": "Rua Exemplo",
                            "numero": "123",
                            "bairro": "Centro",
                            "cep": "87564330",
                            "cidade": "Cidade Exemplo",
                            "municipio": "Aracaju",
                            "complemento": "Perto de tal lugar"
                          },
                          "peridomicilio": "SIM",
                          "intradomicilio": "NAO",
                          "comodoCaptura": "Quarto",
                          "vestigios": "FEZES",
                          "insetifugo": "SIM",
                          "numeroInsetos": 5,
                          "condicao": "VIVO"
                        }
                        """
                                    )
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Amostra criada com sucesso",
                    content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = RetornoEscorpiaoDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Erro de validação ou dados inválidos",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Erro 400 - Amostra ESCORPIAO inválida",
                                            value = """
                                            {
                                              "mensagem": "Amostras do tipo ESCORPIÃO não podem estar esmagadas",
                                              "status": "BAD_REQUEST"
                                            }
                                        """
                                    ),
                                    @ExampleObject(
                                            name = "Erro 400 - Amostra TRIATOMINEO inválida",
                                            value = """
                                            {
                                              "mensagem": "Amostras do tipo TRIATOMINEOS não podem ter mais de 48 horas desde a coleta",
                                              "status": "BAD_REQUEST"
                                            }
                                        """
                                    ),
                                    @ExampleObject(
                                            name = "Erro 400 - Amostra MOLUSCO inválida",
                                            value = """
                                            {
                                              "mensagem": "Amostras do tipo MOLUSCO não podem ter mais de 12 horas desde a coleta",
                                              "status": "BAD_REQUEST"
                                            }
                                        """
                                    ),
                                    @ExampleObject(
                                            name = "Erro 400 - Requisição inválida",
                                            value = """
                                            {
                                              "mensagem": "Tipo de amostra inválida",
                                              "status": "BAD_REQUEST"
                                            }
                                        """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Erro 500",
                                    value = """
                    {
                      "mensagem": "Erro inesperado ao cadastrar amostra",
                      "status": "INTERNAL_SERVER_ERROR"
                    }
                """
                            )
                    )
            )
    })
    @PostMapping
    public ResponseEntity<?> cadastrarAmostra(@org.springframework.web.bind.annotation.RequestBody AmostraDto dto, UriComponentsBuilder uriComponentsBuilder) {
        var amostra = amostraService.cadastrarAmostraUnificada(dto);

        Long id = ((RetornoIdAmostras) amostra).id();

        var uri = uriComponentsBuilder.path("cadastro/amostra/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).body(amostra); // 201 created
    }


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Amostra deletada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = "Amostra removida!")
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Protocolo inválido ou não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Erro 404 - Protocolo inválido ou inexistente",
                                            value = """
                                            {
                                              "mensagem": "Protocolo inválido ou inexistente",
                                              "status": "BAD_REQUEST"
                                            }
                                        """
                                    )
                            }
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Amostra está associada a um lote e não pode ser deletada",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Erro 400 - Requisição inválida",
                                            value = """
                                            {
                                              "mensagem": "A amostra AMOSTRA está associada a um lote e não pode ser deletada",
                                              "status": "BAD_REQUEST"
                                            }
                                        """
                                    )
                            }
                    )
            )
    })
    @Operation(summary = "Deletar Amostra", description = "Metodo para deletar uma amostra existente pelo numero do protocolo", tags = "Gerenciar Amostras")
    @DeleteMapping("/{protocolo}")
    public ResponseEntity<String> removerAmostra(@PathVariable Long protocolo) {
        amostraService.deletarAmostra(protocolo);
        return ResponseEntity.ok("Amostra removida!");
    }


    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Amostra retornada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RetornoEscorpiaoDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Protocolo ou tipo de amostra inválidos ou não encontrados",
                    content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Erro 404 - Protocolo inválido ou inexistente",
                                            value = """
                                            {
                                              "mensagem": "Protocolo inválido ou inexistente",
                                              "status": "BAD_REQUEST"
                                            }
                                        """
                                    ),
                                    @ExampleObject(
                                            name = "Erro 404 - Tipo de amostra não encontrada",
                                            value = """
                                            {
                                              "mensagem": "Tipo de amostra não encontrada",
                                              "status": "BAD_REQUEST"
                                            }
                                        """
                                    )
                            }
                    )
            )
    })
    @Operation(summary = "Buscar Amostra", description = "Metodo para buscar e exibir informações de uma amostra", tags = "Gerenciar Amostras")
    @GetMapping("/{protocolo}")
    public ResponseEntity<?> buscarAmostra(@PathVariable Long protocolo){
        var amostra = amostraService.buscarAmostra(protocolo);

        return ResponseEntity.ok(amostra);
    }

    @Operation(summary = "Listar Amostras", description = "Metodo para buscar e exibir uma lista de amostras cadastradas", tags = "Gerenciar Amostras")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de amostras retornada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ProtocoloAmostraDto.class)
            )),
            @ApiResponse(responseCode = "204", description = "Nenhuma amostra encontrada", content = @Content),
    })
    @GetMapping
    public ResponseEntity<List<ProtocoloListaAmostraDto>> listarAmostrasCadastradas(){
        var lista = amostraService.listarTodasAmostras();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }

        return ResponseEntity.ok(lista); // 200 OK com a lista
    }

    @Operation(summary = "Envio de laudo (PDF)", description = "Faz o envio do laudo em PDF associado a uma amostra via protocolo", tags = "Gerenciar Amostras")
    @PutMapping(path = "/{protocolo}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> adicionarLaudo(@PathVariable Long protocolo, @RequestParam("file") MultipartFile arquivo) throws IOException {
        amostraService.adicionarLaudo(arquivo, protocolo);
        return ResponseEntity.ok("Laudo adicionado.");
    }

    @Operation(summary = "Download de laudo (PDF)", description = "Faz o download do laudo em PDF associado a uma amostra via protocolo", tags = "Gerenciar Amostras")
    @GetMapping("/{protocolo}/laudo")
    public ResponseEntity<byte[]> baixarLaudo(@PathVariable Long protocolo){
        var amostra = amostraService.retornarAmostra(protocolo);

        byte[] laudo = amostra.getLaudo();
        if (laudo == null || laudo.length == 0) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "laudo-" + protocolo + ".pdf");

        return new ResponseEntity<>(laudo, headers, HttpStatus.OK);

    }

}
