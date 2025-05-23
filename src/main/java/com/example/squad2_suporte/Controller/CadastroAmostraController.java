package com.example.squad2_suporte.Controller;

import com.example.squad2_suporte.dto.amostra.AmostraDto;
import com.example.squad2_suporte.dto.amostra.ProtocoloAmostraDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

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
    @PostMapping
    public ResponseEntity<?> cadastrarAmostra(@org.springframework.web.bind.annotation.RequestBody AmostraDto dto, UriComponentsBuilder uriComponentsBuilder) {
        var amostra = amostraService.cadastrarAmostraUnificada(dto);

        Long id = ((RetornoIdAmostras) amostra).id();

        var uri = uriComponentsBuilder.path("cadastro/amostra/{id}").buildAndExpand(id).toUri();

        return ResponseEntity.created(uri).body(amostra); // 201 created
    }

    @Operation(summary = "Deletar Amostra", description = "Metodo para deletar uma amostra existente pelo numero do protocolo", tags = "Gerenciar Amostras")
    @DeleteMapping("/{protocolo}")
    public ResponseEntity<String> removerAmostra(@PathVariable Long protocolo) {
        amostraService.deletarAmostra(protocolo);
        return ResponseEntity.ok("Amostra removida!");
    }

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
    public ResponseEntity<List<ProtocoloAmostraDto>> listarAmostrasCadastradas(){
        var lista = amostraService.listarTodasAmostras();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }

        return ResponseEntity.ok(lista); // 200 OK com a lista
    }

}
