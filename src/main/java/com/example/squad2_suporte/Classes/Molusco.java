

package com.example.squad2_suporte.Classes;


import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.enuns.Resultado;
import com.example.squad2_suporte.enuns.molusco.TipoMolusco;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "moluscos")
@Getter
@Setter
public class Molusco extends Amostra {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String colecaoHidrica;


    @Column(nullable = false)
    private Integer numMoluscos;


    @Column(nullable = false)
    private TipoMolusco tipoMolusco;


    @Column(nullable = false)
    private Integer numMortos;


    @Column(nullable = false)
    private boolean exposicaoLuz;


    @Column(nullable = false)
    private Boolean esmagamentoConcha;


    @Column(nullable = false)
    private Boolean disseccao;


    @Column(nullable = false)
    private Resultado resultado;




    public Molusco() {
        super();
    }


}
