package com.example.squad2_suporte.dto.amostra;

import com.example.squad2_suporte.enuns.TipoAmostra;

//DTO PARA RETORNAR APENAS O PROTOCOLO DA AMOSTRA E O TIPO NO LOTE
public record ProtocoloAmostraDto(Long protocolo, TipoAmostra tipoAmostra) {
}
