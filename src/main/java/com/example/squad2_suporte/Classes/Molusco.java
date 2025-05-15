package com.example.squad2_suporte.Classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//@Entity
//Table(name = "moluscos")
@Getter
@Setter
public class Molusco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_molusco")
    private Integer idMolusco;

    @Column(name = "identificacao_parcial")
    private String identificacaoParcial;

    @Column(name = "num_caramujos_mortos")
    private Integer numCaramujosMortos;

    @Column(name = "exposicao_a_luz")
    private Integer exposicaoALuz;

    // Adicione outros campos como colunas aqui

    // Construtor padrão (necessário para JPA)
    public Molusco() {
    }

    public Molusco(Integer idMolusco, String identificacaoParcial, Integer numCaramujosMortos, Integer exposicaoALuz) {
        this.idMolusco = idMolusco;
        this.identificacaoParcial = identificacaoParcial;
        this.numCaramujosMortos = numCaramujosMortos;
        this.exposicaoALuz = exposicaoALuz;
    }

    @Override
    public String toString() {
        return "Molusco{" +
                "idMolusco=" + idMolusco +
                ", identificacaoParcial='" + identificacaoParcial + '\'' +
                ", numCaramujosMortos=" + numCaramujosMortos +
                ", exposicaoALuz=" + exposicaoALuz +
                '}';
    }
}
