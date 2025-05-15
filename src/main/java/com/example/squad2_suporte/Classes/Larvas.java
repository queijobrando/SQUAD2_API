package com.example.squad2_suporte.Classes;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//@Entity
//@Table(name = "larvas")
@Getter
@Setter
public class Larvas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_larva")
    private Integer idLarva;

    @Column(name = "tipo_deposito")
    private String tipoDeposito;

    @Column(name = "num_larvas")
    private Integer numLarvas;

    @Column(name = "tipo_larva")
    private String tipoLarva;

    @Column(name = "estado_larva")
    private Boolean estadoLarva;

    @Column(name = "tipo_imovel")
    private String tipoImovel;

    public Larvas() {
}


    public Larvas(Integer idLarva, String tipoDeposito, Integer numLarvas, String tipoLarva,Boolean estadoLarva,String tipoImovel) {
        this.idLarva = idLarva;
        this.tipoDeposito = tipoDeposito;
        this.numLarvas = numLarvas;
        this.tipoLarva = tipoLarva;
        this.estadoLarva = estadoLarva;
    }

    @Override
    public String toString() {
        return "Larvas{" +
                "idLarva=" + idLarva +
                ", tipoDeposito='" + tipoDeposito + '\'' +
                ", numLarvas=" + numLarvas +
                ", tipoLarva=" + tipoLarva +
                '\'' +
                ", estadoLarva=" + estadoLarva +
                '\'' +
                '}';
    }
}

