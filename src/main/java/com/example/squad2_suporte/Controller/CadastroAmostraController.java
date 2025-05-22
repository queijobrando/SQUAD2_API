package com.example.squad2_suporte.Controller;

import com.example.squad2_suporte.dto.amostra.AmostraDto;
import com.example.squad2_suporte.dto.retornotipoamostras.RetornoIdAmostras;
import com.example.squad2_suporte.service.AmostraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("cadastro/amostra")
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
                            "cidade": "Cidade Exemplo",
                            "estado": "SP"
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
                            "cidade": "Cidade Exemplo",
                            "estado": "SP"
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
                            "cidade": "Cidade Exemplo",
                            "estado": "SP"
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
                            "cidade": "Cidade Exemplo",
                            "estado": "SP"
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
                            "cidade": "Cidade Exemplo",
                            "estado": "SP"
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

}
