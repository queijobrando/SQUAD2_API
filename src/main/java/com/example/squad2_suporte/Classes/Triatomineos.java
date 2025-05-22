package com.example.squad2_suporte.Classes;

import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.enuns.triatomineos.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "triatomineos")
@Getter
@Setter
public class Triatomineos extends Amostra {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Peridomicilio peridomicilio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Intradomicilio intradomicilio;

    @Column(nullable = false)
    private String comodoCaptura;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Vestigios vestigios;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Insetifugo insetifugo;

    @Column(nullable = false)
    private Integer numeroInsetos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Condicao condicao;

    // Adicione outros campos como colunas aqui

    // Construtor padrão (necessário para JPA)
    public Triatomineos() {
    }

    /*
    public Triatomineos(TriatomineosDto dados){
        super(new AmostraDto(dados.dataHora(), dados.enderecoDto()));
        this.peridomicilio = dados.peridomicilio();
        this.intradomicilio = dados.intradomicilio();
        this.comodoCaptura = dados.comodoCaptura();
        this.vestigios = dados.vestigios();
        this.insetifugo = dados.insetifugo();
        this.numeroInsetos = dados.numeroInsetos();
        this.condicao = dados.condicao();
        this.setTipoAmostra(TipoAmostra.TRIATOMINEOS); // Seta o tipo da amostra
    }
    */
}

