package com.example.squad2_suporte.dto.amostra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDto {
    private String logradouro;
    private String bairro;
    private String cep;
    private String numero;
    private String complemento;
    private String municipio;
}
