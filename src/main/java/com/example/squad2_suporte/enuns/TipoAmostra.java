package com.example.squad2_suporte.enuns;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoAmostra {
    ESCORPIAO,
    FLEBOTOMINEOS,
    TRIATOMINEOS,
    MOLUSCO,
    LARVAS;

    @JsonValue
    public String getValue() {
        return name();
    }
}