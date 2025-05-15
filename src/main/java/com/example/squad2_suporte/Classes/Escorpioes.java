package com.example.squad2_suporte.Classes;

import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.dto.amostra.AmostraDto;
import com.example.squad2_suporte.dto.enviotipoamostras.EscorpiaoDto;
import com.example.squad2_suporte.enuns.TipoAmostra;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "escorpioes")
@Getter
@Setter
public class Escorpioes extends Amostra {

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private boolean sofreuAcidente;


    // Adicione outros campos como colunas aqui
    public Escorpioes() {
        super();
    }

    /*
    public Escorpioes(EscorpiaoDto dados) {
        super(new AmostraDto(dados.dataHora(), dados.enderecoDto()));
        this.quantidade = dados.quantidade();
        this.sofreuAcidente = dados.sofreuAcidente();
        this.setTipoAmostra(TipoAmostra.ESCORPIAO); // Seta o tipo da amostra
    }
    */
}
