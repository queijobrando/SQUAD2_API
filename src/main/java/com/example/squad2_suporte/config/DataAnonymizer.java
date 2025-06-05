package com.example.squad2_suporte.config;

import com.example.squad2_suporte.Amostras.endereco.Endereco;

public class DataAnonymizer {

    public static String anonymizeAddressPartial(String endereco) {
        if (endereco == null || endereco.isEmpty()) {
            return endereco;
        }
        String[] parts = endereco.split(",");
        if (parts.length >= 3) {
            StringBuilder anonymized = new StringBuilder();
            for (int i = parts.length - 2; i < parts.length; i++) {
                anonymized.append(parts[i].trim()).append(i < parts.length - 1 ? ", " : "");
            }
            return anonymized.toString();
        }
        return endereco;
    }

    public static String anonymizeAddressComplete(String endereco) {
        return null;
    }

    public static Endereco anonymizeEnderecoPartial(Endereco endereco) {
        if (endereco == null) {
            return null;
        }
        endereco.setLogradouro(null);
        endereco.setNumero(null);
        endereco.setComplemento(null);
        endereco.setCep(null);
        return endereco;
    }

    public static Endereco anonymizeEnderecoComplete(Endereco endereco) {
        if (endereco == null) {
            return null;
        }
        endereco.setLogradouro(null);
        endereco.setNumero(null);
        endereco.setComplemento(null);
        endereco.setCep(null);
        endereco.setBairro(null);
        endereco.setMunicipio(null);
        return endereco;
    }
}