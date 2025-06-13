package com.example.squad2_suporte.Classes;


import com.example.squad2_suporte.Amostras.Amostra;
import com.example.squad2_suporte.enuns.larva.TipoLarva;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "larvas")
@Getter
@Setter
public class Larvas extends Amostra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private Integer numLarvas;


    @Column(nullable = false)
    private TipoLarva tipoLarva;


    private String tipoImovel;


    public Larvas(){
        super();
    }


}
